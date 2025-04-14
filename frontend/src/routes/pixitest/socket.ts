import randomName from '@scaleway/random-name';
import { DeckCard, GameContainer, Player } from './script';
import { Application, Assets } from 'pixi.js';

interface PlayerInfo {
	name: string; card: number; id:string;
}

export class Game {
	app = new Application();
	hasPlayed = false;
	playerName = randomName();
	id = randomName("id");
	container?: GameContainer;
	socketSvelte?: WebSocket;

	async init(canvas: HTMLCanvasElement) {
		this.socketSvelte = new WebSocket('ws://localhost:8080/poker');
		this.container = new GameContainer();

		this.socketSvelte!.onmessage = (event) => {
			const data = JSON.parse(event.data);
			const playerInfo: PlayerInfo = data.playerInfo;
			if (data.type == 'join') {
				game.container.addPlayer(playerInfo);
			} else if (data.type == 'quit') {
				game.container.removePlayer(playerInfo);
			} else if (data.type == 'vote') {
				game.container.players[playerInfo.id].setCard(playerInfo.card);
			}
			console.log(game.container.children);

		};


		// put the card asset in the cache
		await Assets.load({
			src: `/cards/Squircle.png`,
			data: {
				parseAsGraphicsContext: true
			}
		});

		// Initialize the application.
		await this.app.init({ background: '#1099bb', resizeTo: window, canvas: canvas });

		// initialize the deck
		const deckCards: DeckCard[] = [];
		for (let i = 0; i < 14; i++) {
			const card = new DeckCard(i);
			deckCards.push(card);
			this.app.stage.addChild(card);

			card.x = this.app.screen.width / 2 + 75 * (i - 7);
			card.y = (3 * this.app.screen.height) / 4;

			card.on('pointerdown', () => {
				game.validate(i);
				for (const dCard of deckCards) {
					dCard.unselect();
				}
				card.select();
			});
		}

		this.container.setSize(this.app.screen.width, this.app.screen.height);

		this.app.stage.addChild(this.container);
	}

	restart() {
		this.validate(-1);
		this.hasPlayed = false;
		this.container.updateHidden(!game.hasPlayed);
	}

	join() {
		this.sendMessage('join');
	}

	validate(selected: number) {
		this.hasPlayed = true;
		this.container.updateHidden(!game.hasPlayed);
		this.sendMessage('vote', selected);
	}

	quit() {
		this.sendMessage('quit');
	}

	private sendMessage(type: string, card: number = -1) {
		this.socketSvelte!.send(JSON.stringify({ type: type, playerInfo: { id : this.id, name: this.playerName, card: card } }));
	}
}

export const game = new Game();
