# Geovanny Cordero Valverde
# B42057


	.data

	.text
		start:

			li $v0, 5
			syscall			# insert ornitorrinco
			move $t0, $v0

			li $v0, 5
			syscall			# insert orden_66
			move $t1, $v0

			li $v0, 5
			syscall			# insert es42
			move $t2, $v0

			move $a0, $t2
			li $v0, 1
			syscall			# print es42

			move $a0, $t1
			li $v0, 1
			syscall			# print orden_66

			li  $v0, 10     # 10 is the exit syscall.
			syscall

main:
		j start
