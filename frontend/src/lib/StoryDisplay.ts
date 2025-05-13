import { Container, Text } from "pixi.js";
import "@pixi/layout";
import { tw } from "@pixi/layout/tailwind";

export class StoryDisplay extends Container {
  private titleText: Text;
  private descriptionText: Text;

  constructor() {
    super();
    this.layout = tw`flex flex-col items-center gap-2`;

    this.titleText = new Text({
      text: "",
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
      text: "",
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
      this.titleText.text = "No User Story selected";
      this.descriptionText.text = "If you are an admin, please select a User Story in the Issue section";
    }
  }		
}
