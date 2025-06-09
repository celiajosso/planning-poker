import {type ClassValue, clsx} from "clsx";
import { writable } from "svelte/store";
import {twMerge} from "tailwind-merge";
import { boolean } from "zod/v4";
import randomName from '@scaleway/random-name';

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs));
}

// eslint-disable-next-line @typescript-eslint/no-explicit-any
export type WithoutChild<T> = T extends { child?: any } ? Omit<T, "child"> : T;
// eslint-disable-next-line @typescript-eslint/no-explicit-any
export type WithoutChildren<T> = T extends { children?: any } ? Omit<T, "children"> : T;
export type WithoutChildrenOrChild<T> = WithoutChildren<WithoutChild<T>>;
export type WithElementRef<T, U extends HTMLElement = HTMLElement> = T & { ref?: U | null };

export let isLogged = writable(false);

export let username = writable(randomName(""));