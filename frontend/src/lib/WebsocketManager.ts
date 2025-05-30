import { Game } from "./Game.svelte";
import type { ServerMessage } from "./ServerMessage";

export namespace WebSocketManager {
  export let socket: WebSocket;

  export function createSocket() {
    if (socket) socket.close()

    const currentHost = window.location.host;
    const wsProtocol = window.location.protocol === "https:" ? "wss:" : "ws:";
    socket = new WebSocket(
      `${wsProtocol}//${import.meta.env.PROD ? currentHost : "localhost:8080"}/poker`,
    );

    socket.onmessage = handler;

    return new Promise((res, rej) => {
      socket.onopen = res;
      socket.onerror = rej;
    });
  }

  function handler(event: MessageEvent) {
    const serverMessage: ServerMessage = JSON.parse(event.data);

    switch (serverMessage.type) {
      case "UserJoined":
        Game.addPlayer(serverMessage.user!);
        break;
      case "UserLeft":
        Game.removePlayer(serverMessage.user!);
        break;
      case "UserUpdated":
        Game.updatePlayer(serverMessage.user!);
        break;
      case "RoomCreated":
      case "RoomJoined":
        Game.storage.user = serverMessage.user!;
        Game.storage.room = serverMessage.room!;
        Game.addPlayer(serverMessage.user!);
        break;
      case "RoomUpdated":
        Game.storage.room = serverMessage.room!;
		Game.updateShowStory()
        break;
    }
  }

  export function sendMessage(
    type: string,
    user: UserDTO | null,
    room: RoomDTO | null,
    story: StoryDTO | null,
  ) {
    let data = JSON.stringify({ type, user, room, story });
    socket.send(data);
  }
}
