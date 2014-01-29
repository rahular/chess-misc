Chess Misc
==========
position_eval.py
----------------
This is a command line tool for quickly evaluating a position in chess.
Here are the basics you need to know to get started :
* **Input** : Any FEN string(s) or a file containing FENs

```python
>> python position_eval.py <fen1> [<fen2>...<fenN>]
OR
>> python position_eval.py file <filename>
(Note : filename must have the extension .fen)
``` 
* **Output** : Current evaluation score and the best move. The FEN(s) along with the evaluation score is also written to a file called *game_moves.log*

plotter.py
----------
You can also plot evaluation score vs number of moves using
```python
>> python plotter.py
```
This will take data from *game_moves.log*

make_dataset.py
---------------
This file takes in a puzzle file in PGN format which contains Mate in 4+ puzzles, parses out the FEN strings, finds the optimal move sequence for mate and prints it to `stdout` and a file named `dataset`
