interface UserDTO {
  id: string;
  username: string;
  role: string;
  roomId: string;
  card: number;
}

interface StoryDTO {
  id: string;
  title: string;
  description: string;
  finalEstimate: string;
  roomId: string;
}

interface RoomDTO {
  id: string;
  name: string;
  storySelected: StoryDTO | null;
  stories: StoryDTO[];
}
