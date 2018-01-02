set.seed(500)
library(MASS)
data <- read.csv(file="output_new.csv")
data1 <-read.csv(file="data.csv")

#dividing in training and testing sets

index <- sample(1:nrow(data),round(0.75*nrow(data)))
train <- data[index,]
test <- data[-index,]

# ---------- min-max reduction and scaling for training data -------------

maxs <- apply(data, 2, max) 
mins <- apply(data, 2, min)

scaled <- as.data.frame(scale(data, center = mins, scale = maxs - mins))

#-------------------------------------------------------------------------

train_ <- scaled[index,]
test_ <- scaled[-index,]

#------------  Min-max for input tuple from data.csv  ----------------

myFile="data.csv"
new_train <- read.csv(myFile)

new_maxs <- apply(data[,1:10], 2, max) 
new_mins <- apply(data[,1:10], 2, min)

input_scaled <- as.data.frame(scale(new_train, center = new_mins, scale = new_maxs - new_mins))

#----------------------------------------------------------------------

library(neuralnet)
n <- names(train_)
f <- as.formula(paste("result ~", paste(n[!n %in% "result"], collapse = " + ")))	# Specify that result is the output in traing dataset 
nn <- neuralnet(f,data=train_,hidden=c(8,5),linear.output=T)

#jpeg(file="filename.jpg")
plot(nn)
#dev.print(png,"file.png",width=1000,height=1000)
#dev.off()

pr.nn <- compute(nn,input_scaled)
print(pr.nn)
pr.nn_ <- pr.nn$net.result*(max(data$result)-min(data$result))+min(data$result)
print(pr.nn_)
#test.r <- (test_$result)*(max(data$result)-min(data$result))+min(data$result)

#MSE.nn <- sum((test.r - pr.nn_)^2)/nrow(test_)
