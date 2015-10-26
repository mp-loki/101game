# 101game
101 Card Game

101 game is a variation of MauMau game, popular in Ukraine and Russia. Kids often call in 'Bridge', but it has nothing to o with a real bridge game.

Each player gets 4 cards at the beginning of each deal. Then the first card from deck is put in discard.
Players go one by one, taking cards from a deck and putting them in discard.
The goal is to get rid of all cards in hand.
When a player's hand is empty, deal is ended and all other players count their's points, and a new deal is started.
Points from each deal are summed up.
Game is over when one of players hits more than 101 points.

The rules are:
A card can be put only over the card of same suite or a same rank.
If player has several cards of same rank in hand, he can put them all in discard in a single turn.

User is allowed to take one card from deck at the beginning of each turn.
User must take a card from a deck if he does not have valid move options in hand.
User can skip turn after he has taken a card from deck and he still has cards valid to go in hand.
User ends turn when he has taken a card from deck and has no more valid cards to go

Special cards:
Ace - next player skips turn
8   - next player skips turn and takes two cards for each 8 in turn
7   - next player takes 1 card for each 7 in turn
6   - must be covered by card of same rank or Jack. If a player does not have cards to cover a 6, hetakes cards from deck until he gets one.
Jack - a wildcard. Jack can be put over any other card, regardless of suite or rank. Jack can also cover a 6.
// Player who goes with Jack demands next player for a suite of next card (not yet implemented).

The card points are:
Ace - 15
6     - 0
7     - 0
8     - 0
9     - 0
10    - 10
Jack  - 20
Queen - 10
King  - 10


