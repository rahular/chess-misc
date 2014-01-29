import sys
import subprocess, time

engine = subprocess.Popen(
    './stockfish',
    universal_newlines=True,
    stdin=subprocess.PIPE,
    stdout=subprocess.PIPE,
)

def put(command):
    engine.stdin.write(command+'\n')

def get():
    # using the 'isready' command (engine has to answer 'readyok')
    # to indicate current last line of stdout
    eval_score = ''
    bestmove = ''
    engine.stdin.write('isready\n')
    while True:
        text = engine.stdout.readline().strip()
        if text == 'readyok':
            break
        elif text != '' and text.startswith('info depth'):
            eval_score = text.split()[7]
        elif text != '' and text.startswith('bestmove'):
            bestmove = text.split()[1]
    return float(eval_score), bestmove

def get_score(fen):
    put('position fen ' + fen)
    put('go movetime 1000')
    time.sleep(1.2)

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print 'Atleast one FEN must be provided'
        sys.exit()
    
    args = sys.argv
    del args[0]
    if(args[0] == 'file'):
        if(len(args) < 2):
            print 'Please enter a filename'
            sys.exit()
        else:
            with open(args[1]) as f:
                content = f.readlines()
            args = content        
    
    f = open('game_moves.log','w')
    for fen in args:
        # 8/6pk/8/1R5p/3K3P/8/6r1/8 b - - 0 42
        # fen = fen.split('[')[0]
        fen = fen.strip()
        get_score(fen)
        data = get()
        print data
        
        string = fen + "[" + str(data[0]) + "]\n"
        f.write(string)
    
    f.close();
    put('quit')
