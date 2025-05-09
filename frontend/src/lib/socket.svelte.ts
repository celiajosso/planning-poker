import randomName from '@scaleway/random-name';
import { DeckCard, GameContainer } from './script';
import { Application, Assets } from 'pixi.js';
import { goto } from '$app/navigation';

interface ServerMessage {
	type: String
	user?: UserDTO
	room?: RoomDTO
}


export class UserDTO {
	id: String = "Dummy";
	username: String;
	role: String = "Dummy"
	roomId: String
	card: number = -1

	constructor(username: String, roomId : String) {
		this.username = username
		this.roomId = roomId
	}
}

export class RoomDTO {
	constructor(public id: String, public name: String) { }
}

export class StoryDTO {
	// constructor(public id: String, public name: String) { }
}

export namespace Game {

	let app = new Application();
	let hasPlayed = false;

	export let user: UserDTO
	export let room: RoomDTO

	let container: GameContainer

	let player_cache : UserDTO[]= []

	function addPlayer(user:UserDTO){
		if (container === undefined){
			player_cache.push(user)
		}
		else {
			container.addPlayer(user);
		}
	}

	function removePlayer(user:UserDTO){
		container?.removePlayer(user);
	}



	export let socket: WebSocket;

	export function createSocket() {
		socket = new WebSocket('ws://localhost:8080/poker');

		socket.onmessage = (event) => {
			const serverMessage: ServerMessage = JSON.parse(event.data);
			if (serverMessage.type == 'UserJoined') {
				addPlayer(serverMessage.user!)
			} else if (serverMessage.type == 'UserLeft') {
				removePlayer(serverMessage.user!);
			} else if (serverMessage.type == 'UserUpdated') {
				container?.players[serverMessage.user.id].setCard(serverMessage.user.card);
			} else if (serverMessage.type == 'RoomCreated') {
				user = serverMessage.user!
				room = serverMessage.room!
				addPlayer(serverMessage.user!)
			} else if (serverMessage.type == 'RoomJoined' || serverMessage.type == 'RoomCreated') {
				user = serverMessage.user!
				room = serverMessage.room!
				addPlayer(serverMessage.user!)
			}
		};

		return new Promise((res, rej) => {
			socket.onopen = res
			socket.onerror = rej
		})
	}

	export async function init(canvas: HTMLCanvasElement) {
		// put the card asset in the cache
		await Assets.load({
			src: `/cards/Squircle.png`,
			data: {
				parseAsGraphicsContext: true
			}
		});

		container = new GameContainer()

		// Initialize the application.
		await app.init({ background: '#1099bb', resizeTo: window, canvas: canvas });

		// initialize the deck
		const deckCards: DeckCard[] = [];
		for (let i = 0; i < 14; i++) {
			const card = new DeckCard(i);
			deckCards.push(card);
			app.stage.addChild(card);

			card.x = app.screen.width / 2 + 75 * (i - 7);
			card.y = (3 * app.screen.height) / 4;

			card.on('pointerdown', () => {
				validate(i);
				for (const dCard of deckCards) {
					dCard.unselect();
				}
				card.select();
			});
		}

		container.setSize(app.screen.width, app.screen.height);

		app.stage.addChild(container);


		for (let player of player_cache){
			container.addPlayer(player)
		}

		player_cache = []
	}

	export function restart() {
		validate(-1);
		hasPlayed = false;
		container!.updateHidden(hasPlayed);
	}

	export function createRoom(user: UserDTO, room: RoomDTO) {
		sendMessage('RoomCreate', user, room, null);
	}

	export function joinRoom(user: UserDTO) {
		sendMessage('RoomJoin', user, null, null);
	}

	export function updateRoom(room: RoomDTO) {
		sendMessage('RoomUpdate', null, room, null);
	}

	export function updateUser(user: UserDTO) {
		sendMessage('UserUpdate', user, null, null);
	}

	export function validate(selected: number) {
		hasPlayed = true;
		container!.updateHidden(hasPlayed);
		user.card = selected
		sendMessage('UserUpdate', user, null, null);
	}

	export function quitRoom() {
		sendMessage('RoomQuit', null, null, null);
		goto('/');
	}

	function sendMessage(type: string, user: UserDTO | null, room: RoomDTO | null, story: StoryDTO | null) {
		let data = JSON.stringify({ type, user, room, story })
		socket.send(data);
	}
}
