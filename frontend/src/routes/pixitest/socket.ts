import randomName from '@scaleway/random-name';
import { DeckCard, GameContainer, Player } from './script';
import { Application, Assets, Text } from 'pixi.js';

interface PlayerInfo {
	name: string;
	card: number;
	id: string;
}

interface Story {
	summary: string;
	description?: string;
	points?: number;
	playerId?: string; 
}

export class Game {
	app = new Application();
	hasPlayed = false;
	playerName = randomName();
	id = randomName("id");
	container?: GameContainer;
	socketSvelte?: WebSocket;
	votes: PlayerInfo[] = [];
	stories: Story[] = [];

	async init(canvas: HTMLCanvasElement) {
		console.log("11111111111111");
		this.socketSvelte = new WebSocket('ws://localhost:8080/poker');
		console.log("222222222222");
		this.container = new GameContainer();
		console.log("tessttt");

		this.socketSvelte!.onmessage = (event) => {
			console.log("33333333333333");

			const data = JSON.parse(event.data);
			const playerInfo: PlayerInfo = data.playerInfo;
			console.log("tessttt", data.type);

			if (data.type === 'join') {
				game.container.addPlayer(playerInfo);

			} else if (data.type === 'quit') {
				game.container.removePlayer(playerInfo);

			} else if (data.type === 'vote') {
				game.container.players[playerInfo.id].setCard(playerInfo.card);

				const existing = this.votes.findIndex(p => p.id === playerInfo.id);
				if (existing >= 0) {
					this.votes[existing] = playerInfo;
				} else {
					this.votes.push(playerInfo);
				}

			} 
			else if (data.type === 'story') {
				const storySummary = playerInfo.name;
				const storyPoints = playerInfo.card;
			
				const existingIndex = this.stories.findIndex(
					s => s.summary === storySummary && s.playerId === playerInfo.id
				);
			
				if (existingIndex === -1) {
					this.stories.push({
						summary: storySummary,
						points: storyPoints,
						playerId: playerInfo.id
					});
			
					this.renderAllStoryCards(); 
				}
			}
			
			

			console.log(game.container.children);
		};

		await Assets.load({
			src: `/cards/Squircle.png`,
			data: { parseAsGraphicsContext: true }
		});

		await this.app.init({ background: '#1099bb', resizeTo: window, canvas: canvas });

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

	addStory(summary: string, points: number) {
		const existingIndex = this.stories.findIndex(story => story.summary === summary && story.points === points);
		if (existingIndex === -1) {
			const story: Story = { summary, points };
			// this.stories.push(story);
			console.log("Liste des stories", this.stories);
			// this.renderAllStoryCards();

			this.sendMessage('story', -1, story);
		} else {
			console.log("Story déjà existante :", summary, points);
		}
	}

	private renderAllStoryCards() {
		console.log("Rendering all story cards...");
		this.app.stage.children = this.app.stage.children.filter(child => !(child instanceof DeckCard || child instanceof Text));
	
		this.stories.forEach((story, index) => {
			if (story.points > -1) {
				const x = 300 + 120 * index;
				const y = 300;
	
				const storyCard = new DeckCard(story.points);
				storyCard.position.set(x, y);
				this.app.stage.addChild(storyCard);
	
				const storyText = new Text(story.summary, {
					fontSize: 20,
					fill: 'white',
					fontFamily: 'righteous'
				});
				storyText.position.set(x, y + 140);
				this.app.stage.addChild(storyText);
			}
		});
	}
	private sendMessage(type: string, card: number = -1, story?: Story) {
		if (story) {
			console.log("Adding storyINFO", story);

			const message: any = {
				type:"story",
				playerInfo: { id: this.id, name: story.summary, card: story.points }
			};
			console.log("Avant message", message);

			this.socketSvelte!.send(JSON.stringify(message));
		} else {
			console.log("Adding message", type, card);
			const message: any = {
				type,
				playerInfo: { id: this.id, name: this.playerName, card }
			};

			this.socketSvelte!.send(JSON.stringify(message));
		}
	}

	private displayStoryCard(summary: string, points: number) {
		const x = 300 + 120 * this.stories.length;
		const y = 300;

		const storyCard = new DeckCard(points);
		storyCard.position.set(x, y);
		this.app.stage.addChild(storyCard);

		const storyText = new Text(summary, {
			fontSize: 20,
			fill: 'white',
			fontFamily: 'righteous'
		});
		storyText.position.set(x, y + 140);
		this.app.stage.addChild(storyText);
	}
	
}

export const game = new Game();
