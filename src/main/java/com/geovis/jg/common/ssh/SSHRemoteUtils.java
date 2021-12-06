package com.geovis.jg.common.ssh;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

public class SSHRemoteUtils {
    @Value("${ipAddress}")
    private String ipAddress;

    @Value("${userName}")
    private String userName;

    @Value("${password}")
    private String password;

    private static final int DEFAULT_PORT = 22;// 默认端口号

    private int port;// 端口号

    private Session session;// JSCH session

    private boolean logined = false;// 是否登陆
    // 私有的对象
    private static SSHRemoteUtils sshRemoteUtils;

    //
    // 私有的构造方法
    //
    private SSHRemoteUtils() {
    }

    // 懒汉式,线程不安全,适合单线程
    public static SSHRemoteUtils getInstance() {
        if (sshRemoteUtils == null) {
            sshRemoteUtils = new SSHRemoteUtils();
        }
        return sshRemoteUtils;
    }

    // 懒汉式,线程安全,适合多线程
    public static synchronized SSHRemoteUtils getInstance2() {
        if (sshRemoteUtils == null) {
            sshRemoteUtils = new SSHRemoteUtils();
        }
        return sshRemoteUtils;
    }

    /**
     * 构造方法,可以直接使用DEFAULT_PORT
     *
     * @param ipAddress
     * @param userName
     * @param password
     */
    public SSHRemoteUtils(String ipAddress, String userName, String password) {
        this(ipAddress, DEFAULT_PORT, userName, password);
    }

    /**
     * 构造方法,方便直接传入ipAddress,userName,password进行调用
     *
     * @param ipAddress
     * @param port
     * @param userName
     * @param password
     */
    public SSHRemoteUtils(String ipAddress, int port, String userName, String password) {
        super();
        this.ipAddress = ipAddress;
        this.userName = userName;
        this.password = password;
        this.port = port;
    }

    /**
     * 远程登陆
     *
     * @throws Exception
     */
    public void sshRemoteCallLogin(String ipAddress, String userName, String password) throws Exception {
        // 如果登陆就直接返回
        if (logined) {
            return;
        }
        // 创建jSch对象
        JSch jSch = new JSch();
        try {
            // 获取到jSch的session, 根据用户名、主机ip、端口号获取一个Session对象
            session = jSch.getSession(userName, ipAddress, DEFAULT_PORT);
            System.out.println(userName + "======");
            // 设置密码
            session.setPassword(password);

            // 方式一,通过Session建立连接
            // session.setConfig("StrictHostKeyChecking", "no");
            // session.connect();

            // 方式二,通过Session建立连接
            // java.util.Properties;
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);// 为Session对象设置properties
            // session.setTimeout(3000);// 设置超时
            session.connect();//// 通过Session建立连接
            // 设置登陆状态
            logined = true;
        } catch (JSchException e) {
            // 设置登陆状态为false
            logined = false;
            throw new Exception(
                    "主机登录失败, IP = " + ipAddress + ", USERNAME = " + userName + ", Exception:" + e.getMessage());
        }
    }

    /**
     * 关闭连接
     */
    public void closeSession() {
        // 调用session的关闭连接的方法
        if (session != null) {
            // 如果session不为空,调用session的关闭连接的方法
            session.disconnect();
        }

    }

    /**
     * 执行相关的命令
     *
     * @param command
     * @throws IOException
     */
    public void execCommand(String command) throws IOException {
        InputStream in = null;// 输入流(读)
        Channel channel = null;// 定义channel变量
        try {
            // 如果命令command不等于null
            if (command != null) {
                // 打开channel
                //说明：exec用于执行命令;sftp用于文件处理
                channel = session.openChannel("exec");
                // 设置command
                ((ChannelExec) channel).setCommand(command);
                // channel进行连接
                channel.connect();
                // 获取到输入流
                in = channel.getInputStream();
                // 执行相关的命令
                String processDataStream = processDataStream(in);
                // 打印相关的命令
                System.out.println("1、打印相关返回的命令: " + processDataStream);
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }

    }

    /**
     * 对将要执行的linux的命令进行遍历
     *
     * @param in
     * @return
     * @throws Exception
     */
    public String processDataStream(InputStream in) throws Exception {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String result = "";
        try {
            while ((result = br.readLine()) != null) {
                sb.append(result);
                // System.out.println(sb.toString());
            }
        } catch (Exception e) {
            throw new Exception("获取数据流失败: " + e);
        } finally {
            br.close();
        }
        return sb.toString();
    }

    /**
     * 上传文件 可参考:https://www.cnblogs.com/longyg/archive/2012/06/25/2556576.html
     *
     * @param directory  上传文件的目录
     * @param uploadFile 将要上传的文件
     */
    public void uploadFile(String directory, String uploadFile, String upPath) {
        try {
            // 打开channelSftp
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            // 远程连接
            channelSftp.connect();
            String path1 = upPath.substring(0, upPath.lastIndexOf("/"));
            String path = directory + "/" + path1;

            try {
                channelSftp.cd(path);
            } catch (SftpException e) {
                String[] dirs = path.split("/");
                mkdirDir(dirs, "", dirs.length, 0, channelSftp);
            }

/*            // 将文件进行上传(sftp协议)
            // 采用默认的传输模式:OVERWRITE
            channelSftp.cd(path);*/
            File file = new File(uploadFile + path1);
            File[] files = file.listFiles();
            if (files == null || files.length < 1) {
                return;
            }

            for (int i = 0; i < files.length; i++) {
                channelSftp.put(new FileInputStream(files[i]), files[i].getName());
            }


            // 切断远程连接
            channelSftp.exit();
            System.out.println("2、" + file.getName() + " 文件上传成功.....");
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void mkdirDir(String[] dirs, String path, int length, int index, ChannelSftp sftp) {
        index++;
        if (index < length) {
            path += "/" + dirs[index];
        }

        try {
            sftp.cd(path);
            if (index < length) {
                mkdirDir(dirs, path, length, index, sftp);
            }
        } catch (SftpException e) {
            try {
                sftp.mkdir(path);
                sftp.cd(path);
            } catch (SftpException ex) {
                ex.printStackTrace();
            }
            mkdirDir(dirs, path, length, index, sftp);
        }
    }

    /**
     * 下载文件 采用默认的传输模式：OVERWRITE
     *
     * @param src linux服务器文件地址
     * @param dst 本地存放地址
     * @throws JSchException
     * @throws SftpException
     */
    public void fileDownload(String src, String dst) {
        ChannelSftp channelSftp = null;
        try {
            // src 是linux服务器文件地址,dst 本地存放地址
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            // 远程连接
            channelSftp.connect();
            // 下载文件,多个重载方法
            dst = dst.substring(0, dst.lastIndexOf("/"));
            Vector ls = channelSftp.ls(src);
            Iterator iterator = ls.iterator();
            while (iterator.hasNext()) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry) iterator.next();
                String filename = file.getFilename();
                if (filename == null || filename.equals(".") || filename.equals("src/main")) {
                    continue;
                }
                //InputStream inputStream = channelSftp.get(src + filename);
                channelSftp.get(src + "/" + filename, "D:"+dst);
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            // 切断远程连接,quit()等同于exit(),都是调用disconnect()
            channelSftp.quit();
        }
        // channelSftp.disconnect();
        System.out.println("3、" + src + " ,下载文件成功.....");
    }

    /**
     * 删除文件
     * @throws SftpException
     * @throws JSchException
     */
    public void deleteFile(String directoryFile) throws SftpException, JSchException {
        // 打开openChannel的sftp
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        // 远程连接
        channelSftp.connect();
        // 删除文件
        channelSftp.rm(directoryFile);
        //channelSftp.rmdir();
        // 切断远程连接
        channelSftp.exit();
        System.out.println("4、" + directoryFile + " 删除的文件.....");
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @throws SftpException
     * @throws JSchException
     */
    public Vector listFiles(String directory) throws JSchException, SftpException {
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        // 远程连接
        channelSftp.connect();
        // 显示目录信息
        Vector ls = channelSftp.ls(directory);
        //System.out.println("5、" + ls);
        // 切断连接
        channelSftp.exit();
        return ls;
    }
}


