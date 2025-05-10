export class UserDTO {
	id: string = 'Dummy';
	username: string;
	role: string = 'Dummy';
	roomId: string;
	card: number = -1;

	constructor(username: string, roomId: string) {
		this.username = username;
		this.roomId = roomId;
	}
}
