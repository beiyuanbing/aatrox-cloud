package com.aatrox.common.utils;

import org.springframework.util.ResourceUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author aatrox
 * @desc 验证码工具
 * @date 2019/9/4
 */
public class VerifyCodeBuiler {
    public final static String CONF_DIR_PATH = System.getProperty("user.home") + File.separator + "temp";

    /**图片默认路径**/
    private final static String DEFAULT_PICT_PATH = "classpath:code";
    private static File[] files;
    /**
     * 定义图片的width
     **/
    private int width;
    /**
     * 定义图片的height
     **/
    private int height;
    /**
     * 定义图片上显示验证码的个数
     **/
    private int codeCount=4;
    /**
     * 二级图片文件目录的总数;
     */
    private int SECOND_DIR_NUMS = 0;
    /**
     * 图片默认的width
     **/
    private int WIDTH_DEFAULT = 150;
    /**12306图片验证码的默认长度**/
    private static  int PICT_WIDTH_DEFAULT = 300;
    /**定义默认图片的height**/
    private static int HEIGHT_DEFAULT = 90;
    /**12306图片验证码的默认高度**/
    private static int PICT_HEIGHT_DEFAULT = 200;
    /**字体大小**/
    private int fontSize = 18;
    private char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    /**
     * 随机类
     */
    private Random random = new Random();

    {
        width = WIDTH_DEFAULT;
        height = HEIGHT_DEFAULT;

        files = getFiles();
        if (files != null) {
            //二级目录的总数
            SECOND_DIR_NUMS = files.length;
        }
    }

    /**
     * 第一个图片范围 (5,43)~(75,113)  第二个图片范围 (80,43)~(150,113) 第三个图片范围 (155,43)~(225,113) 第四个图片范围 (230,43)~(300,113)
     * 第五个图片范围 (5,118)~(75,188) 第六个图片范围 (80,118)~(150,188) 第七个图片范围 (155,118)~(225,188) 第八个图片范围 (230,118)~(300,188)*/
    public static void main(String[] args) throws Exception {
       /* new VerifiyCodeBuiler().setCodeCount(5);

        //创建文件输出流对象
        OutputStream out = new FileOutputStream(CONF_DIR_PATH + "/" + System.currentTimeMillis() + ".jpg");
        CodeBean codeBean = new VerifiyCodeBuiler().setCodeCount(5).generateSimpleCode();
        ImageIO.write(codeBean.getCodeImage(), "jpeg", out);
        System.out.println(codeBean.getCode());
        Random random = new Random();

        System.out.println(new VerifiyCodeBuiler().checkSimpleCode("6itm", "6itm"));*/
        //new VerifiyCodeBuiler().getFileList("classpath:code");
        CodeBean codeBean = new VerifyCodeBuiler().generatePictureCode(1);
        System.out.println(codeBean.getCode());
        System.out.println(codeBean.getRigthSelectList());

        System.out.println(new VerifyCodeBuiler().checkPictureCodePass("160|90,180|150", Arrays.asList(3, 7)));

    }
    /**
     * 模仿12306生成图片的验证码 图片大小305x193
     * 第一个图片范围 (5,43)~(75,113)
     * 第二个图片范围 (80,43)~(150,113)
     * 第三个图片范围 (155,43)~(225,113)
     * 第四个图片范围 (230,43)~(300,113)
     * 第五个图片范围 (5,118)~(75,188)
     * 第六个图片范围 (80,118)~(150,188)
     * 第七个图片范围 (155,118)~(225,188)
     * 第八个图片范围 (230,118)~(300,188)
     * @return
     */
    public CodeBean generatePictureCode() {
        return generatePictureCode(8, 1);
    }

    /**
     * 模仿12306生成图片的验证码
     * chooseCount不能超过2
     *
     * @return
     */
    public CodeBean generatePictureCode(int chooseCount) {
        return generatePictureCode(8, chooseCount);
    }

    /**
     * 模仿12306生成图片的验证码
     *
     * @param codeCount 图片个数
     * @return
     */
    private CodeBean generatePictureCode(int codeCount, int chooseCount) {
        //图片的默认宽度和高度
        width = PICT_WIDTH_DEFAULT;
        height = PICT_HEIGHT_DEFAULT;
        TempInfo imagesList = getImagesList(codeCount);
        LocationInfo locations = getLocations(imagesList.getTipsList(), chooseCount);
        BufferedImage bufferedImage = mergeImage(imagesList.getFinalFiles(), locations.getTitleTips());
        return new CodeBean().setCodeImage(bufferedImage).setCodeImageBase64(getImgBase64Str(bufferedImage))
                .setCode(StringUtils.join(locations.getTitleTips(), ","))
                .setRigthSelectList(locations.getRightSelectList());
    }

    /**
     * 获取文件目录list
     *
     * @param
     * @return
     */
    public TempInfo getImagesList(int codeCount) {
        System.out.println();
        //保存取到的每一个图片的path，保证图片不会重复
        List<String> paths = new ArrayList<>();
        List<File> finalImages = new ArrayList<File>();
        //保存tips
        List<String> tips = new ArrayList<String>();
        for (int i = 0; i < codeCount; i++) {
            //获取随机的二级目录
            int dirIndex = getRandom(SECOND_DIR_NUMS);
            File secondaryDir = files[dirIndex];
            //获取二级图片目录下的文件
            File[] images = secondaryDir.listFiles();
            int imageIndex = getRandom(images.length);
            File image = images[imageIndex];
            if (!paths.contains(image.getPath())) {
                paths.add(image.getPath());
                tips.add(secondaryDir.getName());
                finalImages.add(image);
            } else {
                i--;
            }
        }
        return new TempInfo().setTipsList(tips).setFinalFiles(finalImages);
    }

    /**
     * chooseCount不能超过2,超过2默认为2
     * 默认随机获取tip，返回要选哪几个位置的图片
     */
    public LocationInfo getLocations(List<String> tips, int chooseCount) {
        chooseCount = chooseCount > 2 ? 2 : chooseCount;
        List<String> titleTip = new ArrayList<>();
        int temp = chooseCount;
        while (temp > 0) {
            String tip = tips.get(getRandom(tips.size()));
            if (titleTip.contains(tip)) {
                continue;
            }
            titleTip.add(tip);
            temp--;
            if (titleTip.size() == chooseCount) {
                break;
            }
        }
        String tip = tips.get(getRandom(tips.size()));
        List<Integer> rightSelectList = new ArrayList<>();
        Stream.iterate(0, i -> i + 1).limit(tips.size()).forEach(i -> {
            if (titleTip.contains(tips.get(i))) {
                rightSelectList.add(i + 1);
            }
        });
        return new LocationInfo().setTitleTips(titleTip).setRightSelectList(rightSelectList);
    }

    /**
     * 绘制图片融合图片
     *
     * @param finalImages
     * @return
     */
    public BufferedImage mergeImage(List<File> finalImages, List<String> tipAnswer) {
        int pict_height_start = 38;
        int defaut_width = 70;
        int defaut_height = 70;
        int lineLength = finalImages.size() / 2;
        int padding=5;
        //读取图片
        BufferedImage mergeImage = new BufferedImage(305, 193, BufferedImage.TYPE_INT_BGR);
        Graphics2D graphics = mergeImage.createGraphics();
        //此处编写文字
        graphics.setBackground(Color.WHITE);
        //通过使用当前绘图表面的背景色进行填充来清除指定的矩形
        graphics.clearRect(0, 0, 305, 193);
        //设置透明  end
        //设置字体
        graphics.setFont(new Font("方正舒体", Font.PLAIN, 20));
        //消除锯齿状
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        //设置颜色
        graphics.setColor(Color.BLACK);
        graphics.drawString("请选择所有的", 0, 26);
        graphics.setColor(Color.RED);
        String tipsStr = StringUtils.join(tipAnswer, ",");
        graphics.drawString(tipsStr, 120, 26);
        //边缘线
        //设置颜色
        graphics.setColor(Color.BLACK);
        graphics.drawLine(0, 36, 300, 36);
        for (int i = 0; i < finalImages.size(); i++) {
            File image = finalImages.get(i);
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(image);
                //从图片中读取RGB
                if (i < lineLength) {
                    graphics.drawImage(bufferedImage, i * (defaut_width+padding)+padding, pict_height_start+padding, defaut_width, defaut_height, null);
                } else {
                    graphics.drawImage(bufferedImage, (i - lineLength) * (defaut_width+padding)+padding, defaut_height + pict_height_start+2*padding, defaut_width, defaut_height, null);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return mergeImage;
    }

    /**
     * 随机数
     * @param n
     * @return
     */
    public int getRandom(int n) {
        return new Random().nextInt(n);
    }

    /**
     * 获取验证码图片
     * @return
     */
    public File[] getFiles() {
        try {
            File parent = ResourceUtils.getFile(DEFAULT_PICT_PATH);
            return parent.listFiles();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 生产验证码
     *
     * @return
     */
    public CodeBean generateSimpleCode() {
        //自定义RGB图片
        BufferedImage codeImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics paint = codeImage.getGraphics();
        //设置填充颜色
        paint.setColor(getRandColor(250, 255));
        //设置大小
        paint.fillRect(0, 0, width, height);

        fontSize = height > 30 ? 30 : height;
        Font font = new Font("Fixedsys", Font.BOLD, fontSize);
        // 设置字体。
        paint.setFont(font);

        //边框
        paint.setColor(Color.BLACK);
        paint.drawRect(0, 0, width - 1, height - 1);

        //1.绘制干扰线条
        for (int i = 0; i < getRandomDrawLine(); i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            paint.drawLine(x, y, x + xl + 40, y + yl + 20);
        }
        StringBuffer codeBuffer = new StringBuffer();
        int xx = width / (codeCount + 2);
        int codeY = height / 2;
        for (int i = 0; i < codeCount; i++) {
            String code = getRandomCode();
            codeBuffer.append(code);
            paint.setColor(getRandColor());
            paint.drawString(code, (i + 1) * xx, codeY + fontSize / 4);
        }
        return new CodeBean().setCode(codeBuffer.toString()).setCodeImage(codeImage).setCodeImageBase64(getImgBase64Str(codeImage));
    }

    /**
     * 检查图片的选择正确与否
     * pointStr 格式:
     * x|y,x1|y2,x3|y3
     * 一个图片的宽度和宽度是75
     *
     * @return
     */
    public boolean checkPictureCodePass(String pointStr, List<Integer> rightSelectList) {
        if (StringUtils.isEmpty(pointStr)) {
            return false;
        }
        //验证码图片开始高度
        int pict_height_start = 38;
        List<Integer> answerList = new ArrayList<>();
        String[] pointArray = pointStr.split(",");
        for (String point : pointArray) {
            String[] pointxy = point.split("\\|");
            Integer x = Integer.valueOf(pointxy[0]);
            Integer y = Integer.valueOf(pointxy[1]) - pict_height_start;
            if (y >= 5 && y < 75) {
                if (x >= 5 && x <= 75) {
                    answerList.add(1);
                } else if (x >= 80 && x <= 150) {
                    answerList.add(2);
                } else if (x >= 155 && x <= 225) {
                    answerList.add(3);
                } else if (x >= 230 && x <= 300) {
                    answerList.add(4);
                }
            } else if (y >= 80 && y <= 150) {
                if (x >= 5 && x <= 75) {
                    answerList.add(5);
                } else if (x >= 80 && x <= 150) {
                    answerList.add(6);
                } else if (x >= 155 && x <= 225) {
                    answerList.add(7);
                } else if (x >= 230 && x <= 300) {
                    answerList.add(8);
                }
            }
        }
        System.out.println("用户选择:" + answerList);
        System.out.println("必须选的:" + rightSelectList);
        return ListUtil.checkSameList(answerList, rightSelectList);
    }

    /**
     *      * 将图片转换成Base64编码
     *      * @param imgFile 待处理图片
     *      * @return
     *     
     */
    public String getImgBase64Str(BufferedImage bufferedImage) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpeg", os);
            data = os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(Base64.getEncoder().encode(data));
    }

    /**
     * 随机的字母
     * @return
     */
    public String getRandomCode() {
        String code = String.valueOf(codeSequence[random.nextInt(36)]);
        if (random.nextInt(2) == 0) {
            code = code.toLowerCase();
        } else {
            code = code.toUpperCase();
        }
        ;
        return code;
    }

    /**
     * 干扰线按范围获取随机数
     *
     * @return
     */
    private int getRandomDrawLine() {
        int min = 20;
        int max = 155;
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 获取随机颜色
     *
     * @param
     * @param
     * @return
     */
    public Color getRandColor() {
        return getRandColor(100, 160);
    }

    private Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 恢复默认的参数
     */
    private void resetInitDefault() {
        codeCount = 4;
        width = WIDTH_DEFAULT;
        height = HEIGHT_DEFAULT;
    }

    /**
     * 进行判断code，是否忽略大小写
     *
     * @param userCode
     * @param modelCode
     * @return
     */
    public boolean checkSimpleCode(String userCode, String modelCode, boolean ignore) {
        //如果模板的modelCode为空，说明是不需要验证码了
        if (modelCode == null || modelCode.trim().equals("")) {
            return true;
        }

        if (userCode == null || userCode.trim().equals("")) {
            return false;
        }

        if (ignore) {
            modelCode = modelCode.trim().toUpperCase();
            userCode = userCode.trim().toUpperCase();
        }
        if (modelCode.trim().equals(userCode.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 进行判断code，默认忽略大小写
     *
     * @param userCode
     * @param modelCode
     * @return
     */
    public boolean checkSimpleCode(String userCode, String modelCode) {
        return checkSimpleCode(userCode, modelCode, true);
    }

    public int getWidth() {
        return width;
    }

    public VerifyCodeBuiler setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public VerifyCodeBuiler setHeight(int height) {
        this.height = height;
        return this;
    }

    public int getCodeCount() {
        return codeCount;
    }

    public VerifyCodeBuiler setCodeCount(int codeCount) {
        this.codeCount = codeCount;
        return this;
    }

    public static class LocationInfo {
        //标题
        private List<String> titleTips;
        //选择的图片
        private List<Integer> rightSelectList;

        public List<String> getTitleTips() {
            return titleTips;
        }

        public LocationInfo setTitleTips(List<String> titleTips) {
            this.titleTips = titleTips;
            return this;
        }

        public List<Integer> getRightSelectList() {
            return rightSelectList;
        }

        public LocationInfo setRightSelectList(List<Integer> rightSelectList) {
            this.rightSelectList = rightSelectList;
            return this;
        }
    }

    public static class CodeBean {
        //验证码的标题，简单的验证为结果
        private String code;

        //验证码图片
        private BufferedImage codeImage;

        //验证码图片字串
        private String codeImageBase64;

        //图片验证码要选择的位置信息
        private List<Integer> rigthSelectList;

        //Base64的标准图片头部格式
        private String codeBase64Head = "data:image/jpeg;base64,";

        public String getCode() {
            return code;
        }

        public CodeBean setCode(String code) {
            this.code = code;
            return this;
        }

        public BufferedImage getCodeImage() {
            return codeImage;
        }

        public CodeBean setCodeImage(BufferedImage codeImage) {
            this.codeImage = codeImage;
            return this;
        }

        public String getCodeImageBase64() {
            return codeImageBase64;
        }

        public CodeBean setCodeImageBase64(String codeImageBase64) {
            this.codeImageBase64 = codeImageBase64;
            return this;
        }

        public List<Integer> getRigthSelectList() {
            return rigthSelectList;
        }

        public CodeBean setRigthSelectList(List<Integer> rigthSelectList) {
            this.rigthSelectList = rigthSelectList;
            return this;
        }

    }

    //
    class TempInfo {
        //tip数组
        private List<String> tipsList;
        //file数组
        private List<File> finalFiles;

        public List<String> getTipsList() {
            return tipsList;
        }

        public TempInfo setTipsList(List<String> tipsList) {
            this.tipsList = tipsList;
            return this;
        }

        public List<File> getFinalFiles() {
            return finalFiles;
        }

        public TempInfo setFinalFiles(List<File> finalFiles) {
            this.finalFiles = finalFiles;
            return this;
        }
    }
}