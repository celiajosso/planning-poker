import { Container, Text } from "pixi.js";
import "@pixi/layout";
import { tw } from "@pixi/layout/tailwind";

export class StoryDisplay extends Container {
  private titleText: Text;
  private descriptionText: Text;

  constructor(title: string, description: string) {
    super();
    this.layout = tw`flex flex-col items-center gap-2`;

    this.titleText = new Text({
      text: title,
      style: {
        fill: "black",
        fontFamily: "Righteous Regular",
        fontSize: 20,
        align: "center",
      },
      layout: {
        width: "intrinsic",
        height: "intrinsic",
      },
    });

    this.descriptionText = new Text({
      text: description,
      style: {
        fill: "gray",
        fontFamily: "Righteous Regular",
        fontSize: 16,
        align: "center",
      },
      layout: {
        width: "intrinsic",
        height: "intrinsic",
      },
    });

    this.addChild(this.titleText);
    this.addChild(this.descriptionText);
  }

  setStory(story: StoryDTO | null) {
    if (story) {
      this.titleText.text = story.title || "Untitled story";
      this.descriptionText.text = story.description || "";
    } else {
      this.titleText.text = "No story selected";
      this.descriptionText.text = "";
    }
  }
}
