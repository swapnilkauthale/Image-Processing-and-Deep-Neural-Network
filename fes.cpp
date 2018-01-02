/*
 g++ -ggdb `pkg-config --cflags opencv` -o `basename fes.cpp .cpp` fes.cpp `pkg-config --libs opencv`
 ./fes
*/
#include <stdio.h>
#include<math.h>
#include <iostream>
#include<fstream>
#include <opencv2/core/core.hpp>
#include <opencv2/opencv.hpp>
#include <opencv2/highgui/highgui.hpp>
#include<string>
#include<stdlib.h>
#define numClusters 2
#define max 8
using namespace cv;
using namespace std;

ifstream fin;
ofstream fs;
cv::Mat src;
void colorContrast(string);
void intensityContrast(string);
void textureFeature(string);
void edgesGradient(string);
int main(int argc,char* argv[])
{
	string imgname;
	imgname=argv[1];
	
	colorContrast(imgname);
	intensityContrast(imgname);
	textureFeature(imgname);
	edgesGradient(imgname);
	        
	cv::waitKey(0);
	return 0;
}

/*************************************************EDGES GRADIENT**************************/
void edgesGradient(string imgname)
{

  Mat src_gray;
  Mat grad;
  int scale = 1;
  int delta = 0;
  int ddepth = CV_16S;

  int c;

  /// Load an image
  src = imread( imgname );
  imwrite( "inter_results/original.jpg", src);

  GaussianBlur( src, src, Size(3,3), 0, 0, BORDER_DEFAULT );

  /// Convert it to gray
  cvtColor( src, src_gray, CV_BGR2GRAY );

  /// Generate grad_x and grad_y
  Mat grad_x, grad_y;
  Mat abs_grad_x, abs_grad_y;

  /// Gradient X
  //Scharr( src_gray, grad_x, ddepth, 1, 0, scale, delta, BORDER_DEFAULT );
  Sobel( src_gray, grad_x, ddepth, 1, 0, 3, scale, delta, BORDER_DEFAULT );
  convertScaleAbs( grad_x, abs_grad_x );
  
  Scalar x = sum(grad_x);
  //cout<<"\n\n\ngrad_x: "<<x[0];
 
  /// Gradient Y
  //Scharr( src_gray, grad_y, ddepth, 0, 1, scale, delta, BORDER_DEFAULT );
  Sobel( src_gray, grad_y, ddepth, 0, 1, 3, scale, delta, BORDER_DEFAULT );
  convertScaleAbs( grad_y, abs_grad_y );
  Scalar y = sum(grad_y);
  //cout<<"\n\n\ngrad_y: "<<y[0];


  double root = 0;
  root = sqrt((y[0]*y[0])+(x[0]*x[0]));
  
  cout<<root<<endl;


  /// Total Gradient (approximate)
  addWeighted( abs_grad_x, 0.5, abs_grad_y, 0.5, 0, grad );
  imwrite( "inter_results/edge1.jpg", grad);
}

/************************TEXTURE FEATURE***************************/

void textureFeature(string imgname)
{
	Mat src_gray;
	src = cv::imread(imgname);
	cvtColor(src,src_gray,CV_BGR2GRAY);
	
	
	for(int i=0;i<src_gray.rows;i++)
	{
		for(int j=0;j<src_gray.cols;j++)
		{
			src_gray.at<unsigned char>(i,j)=src_gray.at<unsigned char>(i,j)/32;
		}	
	}

	int glcm[max][max];
	float GLCM[max][max];
	
	//initialize the glcm matrix	
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			glcm[i][j] = 0;
		}	
	}
	//GLCM: count the horizontal values
	for(int i=0;i<src_gray.rows;i++)
	{
		for(int j=0;j<src_gray.cols-1;j++)
		{
			int x=src_gray.at<unsigned char>(i,j);
			int y=src_gray.at<unsigned char>(i,j+1);
			glcm[x][y]+=1;
		}	
	}
	
	long int sum=0;
	//calculating the sum
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			sum+=glcm[i][j];
		}	
	}
		
	//NORMAILIZATION
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			GLCM[i][j] = (float)glcm[i][j]/(float)sum;
		}	
	}
	//printing the sum of the normalized matrix which should be 1
	double sum1=0;
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			sum1+=GLCM[i][j];
		}	
	}
	

	//CALCUALTING THE CONTRAST
	double contrast=0;
	
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			contrast+=GLCM[i][j] * (i-j)*(i-j);
		}
	}
	//cout<<endl<<"CONTRAST: "<<contrast;
	
	//CALCUALTING THE ENGERY
	double energy=0;
	
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			energy+=(GLCM[i][j] * GLCM[i][j] );
		}
	}
	//cout<<endl<<"ENERGY: "<<energy;
	
	//CALCULATING THE HOMOGENEITY
	double homo=0;
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			homo += GLCM[i][j]/(1+abs(i-j));
		}
	}
	//cout<<"\nHOMOGENEITY: "<<homo;
	
	//CALCULATING THE CORRELATION
	double corr=0;
	double mr=0,mc=0,sr=0,sc=0;
	//row mean
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			mr += i*GLCM[i][j];
		}
	}

	//column mean
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			mc += i*GLCM[j][i];
		}
	}

	//standard deviation row
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			sr += (i-mr)*(i-mr)*GLCM[i][j];
		}
	}
	sr = sqrt(sr);
	//standard deviation column
	for(int i=0;i<max;i++)
	{
		for(int j=0;j<max;j++)
		{
			sc += (i-mc)*(i-mc)*GLCM[j][i];
		}
	}
	sc = sqrt(sc);
	double term1=0;
	//FINAL CALCULATION
	for(int i=0;i<max;i++)
	{	
		for(int j=0;j<max;j++)
		{
			term1 += (i-mr)*(j-mc)*GLCM[i][j];
		}
	}
	corr = term1 / (sr*sc);
	
	cout<<contrast<<endl;
	cout<<energy<<endl;
	cout<<homo<<endl;
	cout<<corr<<endl;
	//cout<<endl;	

}


/*******************************************COLOR CONTRAST*****************************/

void colorContrast(string imgname)
{
	src = cv::imread(imgname);

		cv::Mat kMeansSrc(src.rows * src.cols, 3, CV_32F);
	for( int y = 0; y < src.rows; y++ )
	{
	    for( int x = 0; x < src.cols; x++ )
	    {
		for( int z = 0; z < 3; z++)
		    kMeansSrc.at<float>(y + x*src.rows, z) = src.at<Vec3b>(y,x)[z];
	    }
	}

	cv::Mat labels;
	cv::Mat centers;
	int attempts = 2;
	//perform kmeans on kMeansSrc where numClusters is defined previously as 2
	//end either when desired accuracy is met or the maximum number of iterations is reached
	cv::kmeans(kMeansSrc, numClusters, labels, cv::TermCriteria( CV_TERMCRIT_EPS+CV_TERMCRIT_ITER, 20, 0.5), attempts, KMEANS_PP_CENTERS, centers );

	//create an array of numClusters colors
	int colors[numClusters];
	for(int i = 0; i < numClusters; i++) {
		colors[i] = 255/(i+1);
	}

	std::vector<cv::Mat> layers;

	for(int i = 0; i < numClusters; i++)
	{
	    layers.push_back(cv::Mat::zeros(src.rows,src.cols,CV_32F));
	}

	//use the labels to draw the layers
	//using the array of colors, draw the pixels onto each label image
	for( int y = 0; y < src.rows; y++ )
	{
	    for( int x = 0; x < src.cols; x++ )
	    { 
		int cluster_idx = labels.at<int>(y + x*src.rows,0);
		layers[cluster_idx].at<float>(y, x) = (float)(colors[cluster_idx]);;
	    }
	}

	std::vector<cv::Mat> srcLayers;

	//each layer to mask a portion of the original image
	//this leaves us with sections of similar color from the original image
	for(int i = 0; i < numClusters; i++)
	{
	    layers[i].convertTo(layers[i], CV_8UC1);
	    srcLayers.push_back(cv::Mat());
	    src.copyTo(srcLayers[i], layers[i]);
	}
	

	/******************extracting colors*********************/
	
	float black_count0=0,black_count1=0;
	Vec3f res;
	float r0=0,g0=0,b0=0,r1=0,g1=0,b1=0;
	//counting black pixels in the first image
	for(int y=0;y<srcLayers[0].rows;y++)
	{
        	for(int x=0;x<srcLayers[0].cols;x++)
        	{
        		Vec3f color = srcLayers[0].at<Vec3b>(Point(x,y));
			if(color[0]==0 and color[1]==0 and color[2]==0)
			{
		   		black_count0=black_count0+1;     	
			}
		}
	}
	
	r0=sum(srcLayers[0])[0];
	g0=sum(srcLayers[0])[1];
	b0=sum(srcLayers[0])[2];
	float total_pixel_0=srcLayers[0].total();

	float size0=total_pixel_0-black_count0;
	
	float rmean0=r0/size0;
	float gmean0=g0/size0;
	float bmean0=b0/size0;
	
	for(int y=0;y<srcLayers[1].rows;y++)
	{
        	for(int x=0;x<srcLayers[1].cols;x++)
       		{
        		Vec3f color = srcLayers[1].at<Vec3b>(Point(x,y));
		        if(color[0]==0 and color[1]==0 and color[2]==0)
        		{
   				black_count1=black_count1+1;     	
			}
		}
	}
	r1=sum(srcLayers[1])[0];
	g1=sum(srcLayers[1])[1];
	b1=sum(srcLayers[1])[2];
	float total_pixel_1=srcLayers[1].total();

	float size1=total_pixel_1-black_count1;
	float rmean1=r1/size1;
	float gmean1=g1/size1;
	float bmean1=b1/size1;
	
	float cc=abs(rmean1-rmean0)+abs(bmean1-bmean0)+abs(gmean1-gmean0);
	cout<<cc<<"\n";
	
  /**************************HSV Histogram************************************/
  
  	Mat HSV0,HSV1;
	cvtColor(srcLayers[0],HSV0,CV_RGB2HSV);
  	cvtColor(srcLayers[1],HSV1,CV_RGB2HSV);
  	int count[180];
	
	for(int i=0;i<180;i++)
	{
		count[i]=0;
	}
	int countb=0;
	int countw=0;
	for(int i=0;i<srcLayers[0].rows;i++)
	{
		for(int j=0;j<srcLayers[0].cols;j++)
		{
			Vec3b hsv=HSV0.at<Vec3b>(i,j);
			int H=hsv.val[0]; //hue
			if(hsv.val[0]==0 && hsv.val[1] == 0)
			{
				if(hsv.val[2]==0)
				{
					countb++;
				}
				if(hsv.val[2]==100)
				{
					countw++;
				}			
			}
			else
			{
				count[H]++;
			}
		}
	}
	int maximum=-999;
	int l=0;
	for(int i=0;i<180;i++)
	{
		if(count[i] > maximum)
		{
			maximum = count[i];
			l = i;
		}
	}
	
	for(int i=0;i<180;i++)
	{
		count[i]=0;
	}
	int countb1=0;
	int countw1=0;
	for(int i=0;i<srcLayers[1].rows;i++)
	{
		for(int j=0;j<srcLayers[1].cols;j++)
		{
			Vec3b hsv=HSV1.at<Vec3b>(i,j);
			int H=hsv.val[0]; //hue
			if(hsv.val[0]==0 && hsv.val[1] == 0)
			{
				if(hsv.val[2]==0)
				{
					countb1++;
				}
				if(hsv.val[2]==100)
				{
					countw1++;
				}			
			}
			else
			{
				count[H]++;
			}
		}
	}
	int max1=-999;
	int l1=0;
	for(int i=0;i<180;i++)
	{
		if(count[i] > max1)
		{
			max1 = count[i];
			l1 = i;
		}
	}
	int huec=abs(l1-l);
	cout<<huec<<"\n";
	imwrite( "inter_results/colorcontrast1.jpg", srcLayers[0]);
	imwrite( "inter_results/colorcontrast2.jpg", srcLayers[1]);
}



/*************INTENSITY CONTRAST***********************/

void intensityContrast(string imgname)
{
	src = cv::imread(imgname);
	cv::Mat kMeansSrc(src.rows * src.cols, 3, CV_32F);
	for( int y = 0; y < src.rows; y++ )
	{
    		for( int x = 0; x < src.cols; x++ )
    		{
        		for( int z = 0; z < 3; z++)
            		kMeansSrc.at<float>(y + x*src.rows, z) = src.at<Vec3b>(y,x)[z];
    		}
	}

	cv::Mat labels;
	cv::Mat centers;
	int attempts = 2;
	cv::kmeans(kMeansSrc, numClusters, labels, cv::TermCriteria( CV_TERMCRIT_EPS+CV_TERMCRIT_ITER, 8, 1), attempts, KMEANS_PP_CENTERS, centers );
	int colors[numClusters];
	for(int i = 0; i < numClusters; i++) 
	{
        	colors[i] = 255/(i+1);
	}

	std::vector<cv::Mat> layers;
	for(int i = 0; i < numClusters; i++)
	{
    		layers.push_back(cv::Mat::zeros(src.rows,src.cols,CV_32F));
	}

	for( int y = 0; y < src.rows; y++ )
	{
    		for( int x = 0; x < src.cols; x++ )
    		{ 
        		int cluster_idx = labels.at<int>(y + x*src.rows,0);
		        layers[cluster_idx].at<float>(y, x) = (float)(colors[cluster_idx]);;
    		}
	}

	std::vector<cv::Mat> srcLayers;
	for(int i = 0; i < numClusters; i++)
	{
	    layers[i].convertTo(layers[i], CV_8UC1);
	    srcLayers.push_back(cv::Mat());
	    src.copyTo(srcLayers[i], layers[i]);
	}
	Mat HSV0;
	float Value0=0;
	int countb0=0;
	cvtColor(srcLayers[0],HSV0,CV_RGB2HSV);
  	for(int i=0;i<srcLayers[0].rows;i++)
	{
		for(int j=0;j<srcLayers[0].cols;j++)
		{
			Vec3b hsv=HSV0.at<Vec3b>(i,j);
			Value0+=hsv.val[2]; //Value
			
			if(hsv.val[0]==0 && hsv.val[1] == 0)
			{
				if(hsv.val[2]==0)
				{
					countb0++;
				}
			}
		}
	}
	
	int size0=HSV0.total()-countb0;
	float avgIntensity0=(float)Value0/(float)size0;
	float FinalI0=avgIntensity0/255;
	Mat HSV1;
	float Value1=0;
	int countb1=0;
  	cvtColor(srcLayers[1],HSV1,CV_RGB2HSV);
	for(int i=0;i<srcLayers[1].rows;i++)
	{
		for(int j=0;j<srcLayers[1].cols;j++)
		{
			Vec3b hsv=HSV1.at<Vec3b>(i,j);
			Value1+=hsv.val[2]; //Value
			
			if(hsv.val[0]==0 && hsv.val[1] == 0)
			{
				if(hsv.val[2]==0)
				{
					countb1++;
				}
			}
		}
	}
	
	int size1=HSV1.total()-countb1;
	float avgIntensity1=(float)Value1/(float)size1;
	float FinalI1=avgIntensity1/255;
	cout<<abs(FinalI1-FinalI0)<<"\n";
	cout<<FinalI1<<endl;
	cout<<FinalI0<<endl;
	
	imwrite( "inter_results/intensitycontrast1.jpg", srcLayers[0]);
	imwrite( "inter_results/intensitycontrast2.jpg", srcLayers[1]);
}
