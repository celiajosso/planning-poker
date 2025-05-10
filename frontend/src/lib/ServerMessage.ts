import type { RoomDTO } from './RoomDTO';
import type { UserDTO } from './UserDTO';

export type MessageType =
	| 'UserJoined'
	| 'UserLeft'
	| 'UserUpdated'
	| 'RoomCreated'
	| 'RoomJoined'
	| 'RoomUpdated';

export interface ServerMessage {
	type: MessageType;
	user?: UserDTO;
	room?: RoomDTO;
}
