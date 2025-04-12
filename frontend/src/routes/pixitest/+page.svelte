<script lang="ts">
    import {Application, Assets} from 'pixi.js';
    import {onMount} from 'svelte';
    import {DeckCard, Player} from './script'

    let canvas: HTMLCanvasElement;
    let players: any[] = $state([]) // data that should be providen by the websocket

    $effect(() => {
        // update the game when a change is made
        for (let player of players) {
            player.sprite.hidden(!hasPlayed);
            player.sprite.setCard(player.card);
        }
    })

    let hasPlayed = $state(false);
    const app = new Application();
    let gameStarted = $state(false);

    function validate(selected : number) {
        hasPlayed = true;
        players[0].card = selected;
    }


    function restartGame() {
        hasPlayed = false;
        for (let player of players) {
            player.sprite.setCard(3);
            player.sprite.hidden(false);
        }
        gameStarted = false;
    }

    onMount(async () => {
        // put the card asset in the cache
        await Assets.load({
            src: `/cards/Squircle.png`,
            data: {
                parseAsGraphicsContext: true
            }
        });

        // Intialize the application.
        await app.init({background: '#1099bb', resizeTo: window, canvas: canvas});

        // simulate new play
        setTimeout(() => {
            gameStarted = true;
            console.log("A player arrived");
        }, 5000);

        // simulate a game with ten players
        {
            for (let i = 0; i < 10; i++) {
                let p = new Player("test", 3)
                players.push(
                    {name: "test", card: 3, sprite: p}
                )
                p.position.x = 50 + 120 * i;
                p.position.y = app.screen.height / 4;
                app.stage.addChild(p);
            }
        }
        // simulate a card change
        setTimeout(() => {
            players[1].card = 5
        }, 8000)

        // initialize the deck
        let deckCards = []
        for (let i = 0; i < 14; i++) {
            const card = new DeckCard(i);
            deckCards.push(card);
            app.stage.addChild(card);

            card.x = app.screen.width / 2 + 75 * (i - 7);
            card.y = 3 * app.screen.height / 4;

            card.on('pointerdown', (event) => {
                validate(i)
                for (let dCard of deckCards) {
                    dCard.unselect()
                }
                card.select()
            });
        }

        // add button to restart the game
        setTimeout(() => {
            if (players.every(player => player.card !== 3)) {
                const button = document.createElement('button');
                button.innerHTML = "Restart Game";
                document.body.appendChild(button);
                button.addEventListener('click', restartGame);
            }
        }, 10000);

        // // Add an animation loop callback to the application's ticker.
        // app.ticker.add((time) => {
        // 	/**
        // 	 * Just for fun, let's rotate mr rabbit a little.
        // 	 * Time is a Ticker object which holds time related data.
        // 	 * Here we use deltaTime, which is the time elapsed between the frame callbacks
        // 	 * to create frame-independent transformation. Keeping the speed consistent.
        // 	 */
        // 	bunny.rotation += 0.1 * time.deltaTime;
        // });
    });
</script>

<canvas bind:this={canvas}></canvas>
