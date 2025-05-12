import { Container, Text } from "pixi.js";
import "@pixi/layout";
import { Card } from "./Card";
import { tw } from "@pixi/layout/tailwind";

export class Player extends Container {
  playerName: string;
  readonly card: Card;
  text: Text

  constructor(name: string, card: number) {
    super();
    this.layout = tw`flex flex-col items-center gap-4`;
    this.playerName = name;

    this.text = new Text({
      text: name,
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

    this.card = new Card(card == -1 ? "" : card.toString());
    this.addChild(this.card);
    this.addChild(this.text);
  }

  setCard(card: number) {
    if (card == -1) {
      this.card.select("");
    } else {
      this.card.select(card.toString());
    }
  }

  update(user:UserDTO){
    this.setCard(user.card)
    this.text.text = user.username
  }
}
