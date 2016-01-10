# bushi-shogi

I wrote this a long time ago and am ashamed to say I remember very little about it.

The rough gist is that each of the 24 possible cube orientations is represented by an arbitrary number, and then the full game state can be represented by two numbers representing the state of each cube.

Since there are thus only 576 possible game states, it's not too difficult to go through them all. The code basically iterates over the list of all possible game states repeatedly, first marking those that are obvious losing/winning positions (the 'leaves' of the game tree) and then using that information on the next pass to determine new, less obvious losing/winning positions (those that will be one level higher in the game tree) until every position has been annotated.
