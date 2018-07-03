# Geovanny Cordero Valverde
# B42057


	.data 

	.text
		start:

		li t0, 34
		li $a0, t0
		li $v0, 4
		syscall
	main:
		j start
