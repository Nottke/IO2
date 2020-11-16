# Boxplot

# Escriba aqui los resultados de las ejecuciones de dos algoritmos
# para ser comparados
resultados1 <- c(1169,1063,987,1039,1044,1099,1105,975,1156,991) #orden dado
resultados2 <- c(553,623,597,554,558, 552,528,573,559,564) #vecino mas cercano

# Resultados para parametro alfa
#resultados1 <- c(98635, 88693, 92823, 102833, 97891, 95086, 100768, 91539, 93955, 91434) #alfa 0.98
#resultados2 <- c(85566, 92228, 82581, 92409, 76128, 84312, 93004, 75560, 84186, 84850) #alfa 0.99

# Reemplaze aqui con el nombre del archivo a para crear el boxplot
plotfile = "ejemplo-boxplot.png"

# Crear una matrix para el plot
all.data = cbind(resultados1, resultados2)

# Nombres de las pruebas o algoritmos
colnames (all.data) <- c("Orden dado", "Vecino mas cercano")
#colnames (all.data) <- c("0.98", "0.99")

# data.matrix is a matrix or a data frame
# Customize the plot using par:
# plot.axis.style  0: always parallel to the axis, 1: always horizontal, 2: always perpendicular to the axis, 3: always vertical
# plot.mar margin of the plot (down, left, up, right)
# plot.size.axis / title size of the axis and title
# vertical.x.axis try 1 or 2 
do.boxplot <- function (data.matrix, data.labels=colnames(data.matrix), plot.mar=c(4,14,4,2), 
                        plot.title="Simulated Annealing ejemplo", plot.yaxis.label="Media funcion objetivo",
                        output="sample-boxplot.png", plot.axis.style=1, plot.size.axis=3, 
                        plot.size.title=3, plot.text.yaxis=3, vertical.x.axis=1, 
                        colors=rep(0, ncol(data.matrix))){

  if (ncol(data.matrix)!= length(data.labels)){
    cat ("Error there must be same amount of data sets and data labels.")
    return()
  }
  png (file = output, width = 800, height =  450, units = "px", pointsize = 8, bg = "white")
  par (las=plot.axis.style, mar=plot.mar, cex.axis=plot.size.axis, cex.main=plot.size.title)
  boxplot (data.matrix, main=plot.title, xaxt="n", col=colors)
  mtext (plot.yaxis.label, side=2, line=(plot.mar[2]-3), cex=plot.text.yaxis, las=0)
  axis (1, at=c(1:length(data.labels)), labels=data.labels, line=plot.mar[1] - 3, 
       tick=FALSE, las=vertical.x.axis, cex.axis=plot.size.axis)
  cat ("Plot creado en: ", output, "\n")
  dev.off()
}

#Plot details in the file boxplot.R
do.boxplot(data.matrix=all.data, output=plotfile)
