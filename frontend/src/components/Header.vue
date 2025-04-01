<script setup>
    import Searchbar from './Searchbar.vue';
    import { computed } from 'vue';

    let userState = {
        loggedIn: false,
    }
    const userName = computed(() => userState.loggedIn ? "Jonas" : "Log in/Sign up")
</script>


<template>
    <div id="header">
        <div>
            <h2 id="logo">LOGO</h2>
        </div>
        <div class="container">
            <div id="home">HOME</div>
            <fa icon="home" class="icons"></fa>
        </div>
        <div class="container">
            <div id="categories">CATEGORIES</div>
            <fa icon="icons" class="icons"></fa>
        </div>
        <div class="container">
            <div id="sell">SELL</div>
            <fa icon="arrow-up-from-bracket" class="icons"></fa>
        </div>
        <div id="container">
            <div id="inbox">Inbox</div>
            <fa icon="envelope" id="inbox-icon"></fa>
            <div id="username">{{ userName }}</div>
            <fa icon="user" id="login-icon"></fa>
            <Searchbar id="searchbar"/>
        </div>
    </div>

</template>


<style scoped>
    #header {
        background-color: #E2E5F6;
        height: 133px;
        width: 100%;
        display: grid;
        grid-template-areas: 'logo home categories sell search';
        grid-template-columns: 2fr 1fr 1fr 1fr 3fr;
        align-content: center;
    }
    .container {
        display: flex;
        padding-top:45px;
        justify-content: center;
        align-items: center;
        gap: 10px;
    }
    #container {
        grid-area: search;
        grid-template-areas: 'inbox inbox-icon user user-icon'
                             'searchbar searchbar searchbar searchbar';
        display: grid;
        grid-template-rows: auto auto;
        grid-template-columns: 2fr 1fr 3fr 1fr;
        align-content: center;
        margin-right: 40px;
        row-gap: 10px;
        
    }
    #logo {
        grid-area: logo;
        font-size: 48px;
        font-weight: bold;
    }
    #home {
        grid-area: home;
        font-size: 20px;
    }
    #categories {
        grid-area: categories;
        font-size: 20px;
    }
    #sell {
        grid-area: sell;
        font-size: 20px;
    }
    #inbox {
        grid-area: inbox;
        font-size: 15px;
        justify-self: right;
    }
    #inbox-icon {
        grid-area: inbox-icon;
        font-size: 20px;
        left: 2px;
        justify-self: center;
    }
    #username {
        grid-area: user;
        font-size: 15px;
        justify-self: right;
    }
    #login-icon {
        grid-area: user-icon;
        font-size: 20px;
        padding-right: 10px;
        justify-self: right;
    }

    #searchbar {
        grid-area: searchbar;
    }

    .icons {
        font-size: 25px;
    }
    #logo:hover, #home:hover, #categories:hover, #sell:hover {
        cursor: pointer;
        border-bottom: 2px solid #000;
    }

</style>