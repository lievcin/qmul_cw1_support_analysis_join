# README #

### What is this repository for? ###

Part C of the Coursework1 for Big Data Processing module

### Task ###

The goal of the final part of the coursework is to compute the 30 athletes that received the highest support, according to the Twitter messages of our dataset. 
Please note that there might be a bias towards English speaking countries/athletes, because of the methodology used when collecting the dataset.  

In this final section, you will have to use an additional dataset, containing the list of Rio 2016 athletes that obtained a medal, and their discipline. 
The dataset can be found at:

/data/medalistsrio.csv

The data is saved in Comma(,)-separated-values format, with the first row providing header names. Dataset has been downloaded from 
https://www.kaggle.com/rio2016/olympic-games/data . The dataset has been cropped to only include medalists in an effort to reduce the 
computation time it will impose to the cluster. 

1 - Draw a table with the top 30 athletes in number of mentions across the dataset. For each athlete, include the number of mentions retrieved.  
	For this question you can sort results and compute the top X outside your MapReduce code. [15 marks]
2- Draw a table with the top 20 sports according to the mentions of olympic athletes captured. For resolving athletes into sports use the medalistsrio 
	secondary dataset. For this question you can sort results and compute the top X outside your MapReduce code. [5 marks]

### To build the project run:  ###

### ant clean dist ###
To run the task on the server:
### hadoop jar dist/AthletesSportJoin.jar AthletesSportJoin /data/olympictweets2016rio input ###


