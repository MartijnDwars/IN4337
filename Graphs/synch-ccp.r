setwd('/Users/martijn/TUDelft/Randomized Algorithms (IN4337)/Lab/Graphs')
getwd()

# Synch-CCP, iterations for 100 trials
data <- read.table('/Users/martijn/TUDelft/Randomized Algorithms (IN4337)/Lab/out/production/SYNCH-CCP/emp1.txt', header=TRUE, sep=",")
data_no_outliers <- data$iterations[data$iterations < 10]
h <- hist(data_no_outliers,breaks=c(3:10), probability=TRUE, xaxt="n", right=FALSE, freq=FALSE, main='Distribution of iterations for 10,000 trials', xlab='Iterations')
axis(1, at=h$mids, labels=3:max(data_no_outliers))
lines(c(3.5:10.5), 2*dgeom(c(1:8), prob = 1/2), col='blue', lty=2)
dev.print(postscript, file='synch-ccp-histogram', horizontal=FALSE, onefile=FALSE, paper="special")
dev.off()

boxplot(data)
boxplot(data_no_outliers)

## Observation: for 10,000 trials, the probability distribution almost identically resembles that of a gemetric distribution