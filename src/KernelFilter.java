import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Created by LaraPorts on 26/03/2017.
 */
public class KernelFilter {

    float[][] Kernel = {
        {1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f},
        {1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f},
        {1.0f/9.0f, 1.0f/9.0f, 1.0f/9.0f},
    };

    float[][] Laplace = {
            {0.0f, -1.0f, 0.0f},
            {-1.0f, 4.0f, -1.0f},
            {0.0f, -1.0f, 0.0f},
    };

    float[][] Laplace5 = {
            {0.0f, -1.0f, 0.0f},
            {-1.0f, 5.0f, -1.0f},
            {0.0f, -1.0f, 0.0f},
    };

    float[][] Sharpen = {
            {-1.0f, -1.0f, -1.0f},
            {-1.0f, 9.0f, -1.0f},
            {-1.0f, -1.0f, -1.0f},
    };

    float[][] Border = {
            {-1.0f, -1.0f, -1.0f},
            {-1.0f, 8.0f, -1.0f},
            {-1.0f, -1.0f, -1.0f},
    };

    float[][] Average = {
            {1.0f * (1.f / 9.f), 1.0f * (1.f / 9.f), 1.0f * (1.f / 9.f)},
            {1.0f * (1.f / 9.f), 1.0f * (1.f / 9.f), 1.0f * (1.f / 9.f)},
            {1.0f * (1.f / 9.f), 1.0f * (1.f / 9.f), 1.0f * (1.f / 9.f)},
    };

    float [][] WeightedAverage = {
            {1.5f * (1.f / 18.f), 2.0f * (1.f / 18.f), 1.5f * (1.f / 18.f)},
            {2.0f * (1.f / 18.f), 4.0f * (1.f / 18.f), 2.0f * (1.f / 18.f)},
            {1.5f * (1.f / 18.f), 2.0f * (1.f / 18.f), 1.5f * (1.f / 18.f)},
    };

    double [][] Sobel1 = {
            {Math.pow(-1.0f, 2), Math.pow(-2.0f, 2), Math.pow(-1.0f, 2)},
            {Math.pow(0.0f, 2), Math.pow(0.0f, 2), Math.pow(0.0f, 2)},
            {Math.pow(1.0f, 2), Math.pow(2.0f, 2), Math.pow(1.0f, 2)},
    };

    double [][] Sobel2 = {
            {Math.pow(-1.0f, 2), Math.pow(0.0f, 2), Math.pow(1.0f, 2)},
            {Math.pow(-2.0f, 2), Math.pow(0.0f, 2), Math.pow(2.0f, 2)},
            {Math.pow(-1.0f, 2), Math.pow(0.0f, 2), Math.pow(1.0f, 2)},
    };

    float[][] North = {
            {1.0f, 1.0f, 1.0f},
            {1.0f, -2.0f, 1.0f},
            {-1.0f, -1.0f, -1.0f},
    };

    float[][] Northeast = {
            {1.0f, 1.0f, 1.0f},
            {-1.0f, -2.0f, 1.0f},
            {-1.0f, -1.0f, 1.0f},
    };

    float[][] East = {
            {-1.0f, 1.0f, 1.0f},
            {-1.0f, -2.0f, 1.0f},
            {-1.0f, 1.0f, 1.0f},
    };

    float[][] Southeast = {
            {-1.0f, -1.0f, 1.0f},
            {-1.0f, -2.0f, 1.0f},
            {1.0f, 1.0f, 1.0f},
    };

    float[][] South = {
            {-1.0f, -1.0f, -1.0f},
            {1.0f, -2.0f, 1.0f},
            {1.0f, 1.0f, 1.0f},
    };

    float[][] Southwest = {
            {1.0f, -1.0f, -1.0f},
            {1.0f, -2.0f, -1.0f},
            {1.0f, 1.0f, 1.0f},
    };

    float[][] West = {
            {1.0f, 1.0f, -1.0f},
            {1.0f, -2.0f, -1.0f},
            {1.0f, 1.0f, -1.0f},
    };

    float[][] Northwest = {
            {1.0f, 1.0f, 1.0f},
            {1.0f, -2.0f, -1.0f},
            {1.0f, -1.0f, -1.0f},
    };

    int saturate(int value){
        if(value > 255)
            return 255;
        if(value < 0)
            return 0;
        return value;
    }

    public float[][] generateAverageKernel(int cols, int rows)
    {
        int total = cols * rows;
        float[][] output = new float[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                output[i][j] = 1.0f / total;
            }
        }
        return output;
    }

    public float[][] add(double[][] mat1, double[][] mat2)
    {
        int cols = mat1[0].length;
        int rows = mat1.length;
        float[][] output = new float[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                output[i][j] = (float)mat1[i][j] + (float)mat2[i][j];
            }
        }
        return output;
    }

    public float[][] sqrt(float[][] matrix)
    {
        int cols = matrix[0].length;
        int rows = matrix.length;
        float[][] output = new float[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                output[i][j] = (float)Math.sqrt(matrix[i][j]);
            }
        }
        return output;
    }

    BufferedImage KernelApply(BufferedImage img, float[][] kernel, int size){
        BufferedImage out = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++){
            for (int x = 0; x < img.getWidth(); x++){

                int r = 0, g = 0, b = 0;

                for (int kx = 0; kx < size; kx++) {
                    for (int ky = 0; ky < size; ky++) {
                        int px = x + (kx - 1);
                        int py = y + (ky - 1);

                        if (px < 0 || px >= img.getWidth() || py < 0 || py >= img.getHeight())
                            continue;

                        Color color = new Color(img.getRGB(px, py));
                        r += color.getRed() * kernel[kx][ky];
                        g += color.getGreen() * kernel[kx][ky];
                        b += color.getBlue() * kernel[kx][ky];
                    }
                }
                Color newColor = new Color(saturate(r), saturate(g), saturate(b));
                out.setRGB(x,y,newColor.getRGB());
            }
        }
        return out;
    }

    public void run() throws IOException {
        String PATH = "C:\\Users\\akito\\IdeaProjects\\PDI_Kernel\\img";
        BufferedImage img = ImageIO.read(new File(PATH, "turtle.jpg"));

        //Filtros de Média
        BufferedImage newAverage = KernelApply(img, Average, 3);
        BufferedImage newWeightedAverage = KernelApply(img, WeightedAverage, 3);
        BufferedImage newAverage5 = KernelApply(img, generateAverageKernel(5, 5), 5);
        BufferedImage newAverage9 = KernelApply(img, generateAverageKernel(9, 9), 9);
        BufferedImage newAverage13 = KernelApply(img, generateAverageKernel(13, 13), 13);
        BufferedImage newAverage25 = KernelApply(img, generateAverageKernel(25, 25), 25);

        ImageIO.write(newAverage, "png", new File("img/RGB/average/newAverage.png"));
        ImageIO.write(newWeightedAverage, "png", new File("img/RGB/average/newWeightedAverage.png"));
        ImageIO.write(newAverage5, "png", new File("img/RGB/average/newAverage5.png"));
        ImageIO.write(newAverage9, "png", new File("img/RGB/average/newAverage9.png"));
        ImageIO.write(newAverage13, "png", new File("img/RGB/average/newAverage13.png"));
        ImageIO.write(newAverage25, "png", new File("img/RGB/average/newAverage25.png"));

        //Sharpen e Detecção de Bordas
        BufferedImage newLaplace = KernelApply(img, Laplace,3);
        BufferedImage newLaplace5 = KernelApply(img, Laplace5,3);
        BufferedImage newSharpen = KernelApply(img, Sharpen,3);
        BufferedImage newBorder = KernelApply(img, Border,3);
        BufferedImage newSobel = KernelApply(img, sqrt(add(Sobel1, Sobel2)),3);

        ImageIO.write(newLaplace, "png", new File("img/RGB/sharpen_and_border/newLaplace.png"));
        ImageIO.write(newLaplace5, "png", new File("img/RGB/sharpen_and_border/newLaplace5.png"));
        ImageIO.write(newSharpen, "png", new File("img/RGB/sharpen_and_border/newSharpen.png"));
        ImageIO.write(newBorder, "png", new File("img/RGB/sharpen_and_border/newBorder.png"));
        ImageIO.write(newSobel, "png", new File("img/RGB/sharpen_and_border/newSobel.png"));

        //Filtros Direcionais
        BufferedImage newNorth = KernelApply(img, North,3);
        BufferedImage newNortheast = KernelApply(img, Northeast,3);
        BufferedImage newNorthwest = KernelApply(img, Northwest,3);
        BufferedImage newSouth = KernelApply(img, South,3);
        BufferedImage newSoutheast = KernelApply(img, Southeast,3);
        BufferedImage newSouthwest = KernelApply(img, Southwest,3);
        BufferedImage newEast = KernelApply(img, East,3);
        BufferedImage newWest = KernelApply(img, West,3);

        ImageIO.write(newNorth, "png", new File("img/RGB/directional/newNorth.png"));
        ImageIO.write(newNortheast, "png", new File("img/RGB/directional/newNortheast.png"));
        ImageIO.write(newNorthwest, "png", new File("img/RGB/directional/newNorthwest.png"));
        ImageIO.write(newSouth, "png", new File("img/RGB/directional/newSouth.png"));
        ImageIO.write(newSoutheast, "png", new File("img/RGB/directional/newSoutheast.png"));
        ImageIO.write(newSouthwest, "png", new File("img/RGB/directional/newSouthwest.png"));
        ImageIO.write(newEast, "png", new File("img/RGB/directional/newEast.png"));
        ImageIO.write(newWest, "png", new File("img/RGB/directional/newWest.png"));

        System.out.println("Arroy!");
    }

   public static void main(String[] args) throws IOException {
        new KernelFilter().run();
   }
}
