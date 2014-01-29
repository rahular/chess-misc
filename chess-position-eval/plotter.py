import re
import matplotlib.pyplot as plt

def main() :
	
	with open('./game_moves.log', 'r') as f:
		read_data = f.readlines()
	f.close()
	
	moves = []
	change_eval = []
	num_moves = 0
	for s in read_data :
		split = s.split("[")
		change_eval.append(int(re.match(r'-?\d+', split[1]).group()))
		num_moves += 1
	
	for x in range(0,num_moves):
		moves.append(x)
	
	print moves
	print change_eval
	
	plt.plot(moves, change_eval)
	plt.ylabel('change in eval score')
	plt.xlabel('move number')
	# plt.xlim([0,20])
	# plt.ylim([-5000,5000])
	plt.show()

main()
