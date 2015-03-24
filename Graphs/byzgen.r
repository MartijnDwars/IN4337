setwd('/Users/martijn/TUDelft/Randomized Algorithms (IN4337)/Lab/Graphs')
getwd()

# ByzGen, n = 8, 16, 24, ..., 800, t = 0
data <- read.table('/Users/martijn/TUDelft/Randomized Algorithms (IN4337)/Lab/out/production/BYZGEN/emp1.txt', header=TRUE, sep=",")
plot(data$n, data$iterations, xlab='n', ylab='Iterations', main='Iterations for t = 0', type='b', col='red')

## Observation: for really small n, a single iteration is sufficient. Otherwise, 2 iterations is sufficient. Corresponds with expectation of 2.


# ByzGen, n = 240, t = 1,...,29
data <- read.table('/Users/martijn/TUDelft/Randomized Algorithms (IN4337)/Lab/out/production/BYZGEN/emp2.txt', header=TRUE, sep=",")
plot(data$t, data$iterations, xlab='t', ylab='Iterations', main='Iterations for n = 240', type='b', col='red')

## Observation: once t > n/6 the number of iterations grows rapidly.


# ByzGen, (t,n) = (2*8,1), (3*8, 2), ..., (100*8, 99)
data <- read.table('/Users/martijn/TUDelft/Randomized Algorithms (IN4337)/Lab/out/production/BYZGEN/emp3.txt', header=TRUE, sep=",")
plot(data$t, data$iterations, xlab='t', ylab='Iterations', main='Iterations for n = 4t', type='b', col='red')

model <- nls(iterations ~ C*(1-exp(k*t)), data, algorithm='port', start=c(C=10,k=-1), lower=c(C=-Inf,k=-Inf), upper=c(C=Inf,k=Inf))
summary(model)
pred <- predict(model)
lines(data$t, pred, col='blue', lty=2)
dev.print(postscript, file='byzgen-iterations-constant-ratio.eps', horizontal=FALSE, onefile=FALSE, paper="special")
dev.off()

## Observation: grows rapidly in the beginning, but then looks linear


# Failure probability
data <- read.table('/Users/martijn/TUDelft/Randomized Algorithms (IN4337)/Lab/out/production/BYZGEN/emp4.txt', header=TRUE, sep=",")
data <- data[1:30,]
plot(data$t, data$failing/20, xlab='t', ylab='Failure probability', main='Failure probability for n = 80', type='b', col='red')
dev.print(postscript, file='byzgen-failure-probability.eps', horizontal=FALSE, onefile=FALSE, paper="special")
dev.off()


# Show that it works with t = n/8-1
data <- read.table('/Users/martijn/TUDelft/Randomized Algorithms (IN4337)/Lab/out/production/BYZGEN/emp5.txt', header=TRUE, sep=",")
plot(data$t, data$iterations, xlab='t', ylab='Iterations', main='Iterations for n = 8*(t+1)', type='b', col='red')
dev.print(postscript, file='byzgen-correctly.eps', horizontal=FALSE, onefile=FALSE, paper="special")
dev.off()


# Investigate expectation for n = 40, t = 4 in 1000 trials
data <- read.table('/Users/martijn/TUDelft/Randomized Algorithms (IN4337)/Lab/out/production/BYZGEN/emp7.txt', header=TRUE, sep=",")
plot(c(1:1000), data$iterations, xlab='n', ylab='Iterations', main='Iterations for n = 40, t=4 in 1000 trials', type='l', col='red')
h <- hist(data$iterations, breaks=c(2:6), probability=TRUE, xaxt="n", right=FALSE, freq=FALSE, main='Iterations for n = 40, t = 4', xlab='Iterations')
axis(1, at=h$mids, labels=2:(max(data$iterations)))
dev.print(postscript, file='byzgen-histogram-expectation.eps', horizontal=FALSE, onefile=FALSE, paper="special")
dev.off()
