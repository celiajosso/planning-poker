// import randomName from '@scaleway/random-name';
import { DeckContainer } from './DeckContainer';
import { GameContainer } from './GameContainer';
import { Application, Assets, Container, Texture } from 'pixi.js';
import '@pixi/layout';
import { tw } from '@pixi/layout/tailwind';
import type { RoomDTO } from './RoomDTO';
import type { UserDTO } from './UserDTO';
import { WebSocketManager } from './WebsocketManager';

export namespace Game {
	export let app: Application;
	let hasPlayed = false;

	export let textures: { [k: string]: Texture } = {};

	export let user: UserDTO;
	export let room: RoomDTO;
	export let gameContainer: GameContainer;

	let player_cache: UserDTO[] = [];

	export function addPlayer(user: UserDTO) {
		if (gameContainer === undefined) {
			player_cache.push(user);
		} else {
			gameContainer.addPlayer(user);
		}
	}

	export function removePlayer(user: UserDTO) {
		gameContainer?.removePlayer(user);
	}

	export async function init(canvas: HTMLCanvasElement) {
		app = new Application();
		// put the card asset in the cache
		textures['card'] = await Assets.load({
			src: `/cards/Squircle.png`,
			data: {
				parseAsGraphicsContext: true
			}
		});

		// Initialize the application.
		await app.init({ resizeTo: window, canvas: canvas, background: '#e2e8f0' });

		app.stage.layout = { width: app.screen.width, height: app.screen.height };

		// GlobalContainer

		const globalContainer = new Container({
			layout: tw`flex flex-col items-center justify-between w-full h-dvh m-8 mb-8`
		});

		// GameContainer

		gameContainer = new GameContainer({
			layout: tw`flex flex-row items-center justify-center w-full gap-4 flex-wrap`
		});

		// initialize the deck

		const containerDeck = new DeckContainer(
			{
				layout: tw`flex flex-row items-center justify-center w-full gap-4 flex-wrap`
			},
			validate
		);

		const resizeObserver = new ResizeObserver(() => {
			app.stage.layout = {
				width: app.screen.width,
				height: app.screen.height
			};
		});

		resizeObserver.observe(app.canvas);

		globalContainer.addChild(gameContainer);
		globalContainer.addChild(containerDeck);
		app.stage.addChild(globalContainer);

		for (let player of player_cache) {
			gameContainer.addPlayer(player);
		}

		player_cache = [];
	}

	export function restart() {
		validate(-1);
		hasPlayed = false;
		// gameContainer!.updateHidden(hasPlayed);
	}

	export function createRoom(user: UserDTO, room: RoomDTO) {
		WebSocketManager.sendMessage('RoomCreate', user, room, null);
	}

	export function joinRoom(user: UserDTO) {
		WebSocketManager.sendMessage('RoomJoin', user, null, null);
	}

	export function updateRoom(room: RoomDTO) {
		WebSocketManager.sendMessage('RoomUpdate', null, room, null);
	}

	export function updateUser(user: UserDTO) {
		WebSocketManager.sendMessage('UserUpdate', user, null, null);
	}

	export function validate(selected: number) {
		hasPlayed = true;
		// gameContainer!.updateHidden(hasPlayed);
		user.card = selected;
		WebSocketManager.sendMessage('UserUpdate', user, null, null);
	}

	export function quitRoom() {
		WebSocketManager.sendMessage('RoomQuit', null, null, null);
	}

	export function updatePlayer(user: UserDTO) {
		Game.gameContainer.players[user.id].setCard(user.card);
	}
}
