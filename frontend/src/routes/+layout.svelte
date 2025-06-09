<script lang="ts">
  import { Toaster } from "svelte-sonner";
  import "../app.css";
  import { isLogged, username } from "$lib/utils";
  import { onMount } from "svelte";
  import randomName from "@scaleway/random-name";

  let { children } = $props();

  onMount(() => {
    if (localStorage.getItem("isLogged")) {
      isLogged.set(localStorage.getItem("isLogged") === "true");
      if (localStorage.getItem("username")) {
        username.set(localStorage.getItem("username")!);
      }
    } else {
      username.set(randomName());
    }

    isLogged.subscribe((value: boolean) => {
      if (!value) {
        localStorage.removeItem("isLogged");
        username.set(randomName());
      } else {
        localStorage.setItem("isLogged", String(value));
      }
    });

    username.subscribe((value) => {
      localStorage.setItem("username", value!);
    });
  });
</script>

{@render children()}

<Toaster position="bottom-right" />
