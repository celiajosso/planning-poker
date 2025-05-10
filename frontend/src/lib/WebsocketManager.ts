import { Game } from './Game.svelte';
import type { RoomDTO } from './RoomDTO';
import type { ServerMessage } from './ServerMessage';
import type { StoryDTO } from './StoryDTO';
import type { UserDTO } from './UserDTO';

export namespace WebSocketManager {
	export let socket: WebSocket;

	export function createSocket() {
		socket = new WebSocket(`${import.meta.env.PROD ? "" : "ws://localhost:8080"}/poker`);

		socket.onmessage = handler;

		return new Promise((res, rej) => {
			socket.onopen = res;
			socket.onerror = rej;
		});
	}

	function handler(event: MessageEvent) {
		const serverMessage: ServerMessage = JSON.parse(event.data);

		switch (serverMessage.type) {
			case 'UserJoined':
				Game.addPlayer(serverMessage.user!);
				break;
			case 'UserLeft':
				Game.removePlayer(serverMessage.user!);
				break;
			case 'UserUpdated':
				Game.updatePlayer(serverMessage.user!);
				break;
			case 'RoomCreated':
			case 'RoomJoined':
				Game.user = serverMessage.user!;
				Game.room = serverMessage.room!;
				Game.addPlayer(serverMessage.user!);
				break;
			case 'RoomUpdated':
				break;
		}
	}

	export function sendMessage(
		type: string,
		user: UserDTO | null,
		room: RoomDTO | null,
		story: StoryDTO | null
	) {
		let data = JSON.stringify({ type, user, room, story });
		socket.send(data);
	}
}
