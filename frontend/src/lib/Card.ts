import {Container, Matrix, Sprite, Text, Ticker} from "pixi.js";
import "@pixi/layout";
import {Game} from "./Game.svelte";

export class Card extends Container {
    text: Text;
    public tickerCallback: ((delta: Ticker) => void) | undefined;

    public constructor(card: string) {
        super();

        this.layout = true;

        let texture = Game.textures.Card;

        let w = texture.width / 2.5;
        let h = texture.height / 2.5;

        let sprite = new Sprite({
            texture: texture,
            layout: {
                width: w,
                height: h,
            },
        });

        sprite.anchor.set(0.5);
        sprite.position.set(0.5);

        this.text = new Text({
            text: card,
            style: {
                fontSize: 24,
                fill: "gray",
                fontFamily: "Righteous Regular",
                align: "center",
            },
        });

        this.text.anchor.set(0.5);
        this.text.position.set(w / 2, h / 2);

        this.addChild(sprite);
        this.addChild(this.text);
    }

    setText(text: string): void {
        this.text.text = text;
    }

    select(card: string) {
        let ctx = this;

        const max = 1000;
        const startTime = performance.now();

        if (ctx.tickerCallback) {
            Game.app.ticker.remove(ctx.tickerCallback);
        }
        this.tickerCallback = (delta: Ticker) => {
            const t = performance.now() - startTime;
            if (t > max) {
                if (ctx.tickerCallback) {
                    Game.app.ticker.remove(ctx.tickerCallback);
                    ctx.setFromMatrix(new Matrix(1, 0, 0, 1, 0, 0));
                    ctx.setText(`${card}`);
                }
                return;
            }

            const progress = t / max;
            const scale = Math.abs(Math.cos(progress * Math.PI));
            if (progress > 0.5) {
                ctx.setText(`${card}`);
            }
            // ctx.setSize(scale, 1);
            ctx.setFromMatrix(new Matrix(scale, 0, 0, 1, (1 - scale) / 2, 0));
        };

        Game.app.ticker.add(this.tickerCallback);
    }
}
