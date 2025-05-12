export type MessageType =
  | "UserJoined"
  | "UserLeft"
  | "UserUpdated"
  | "RoomCreated"
  | "RoomJoined"
  | "RoomUpdated";

export interface ServerMessage {
  type: MessageType;
  user?: UserDTO;
  room?: RoomDTO;
  story?: StoryDTO;
}
