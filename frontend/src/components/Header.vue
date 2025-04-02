<script setup>
    import Searchbar from './Searchbar.vue';
    import { ref, computed } from 'vue';

    // Define props so to pass values to test more easily
    defineProps({
        mail: {
            type: Number,
            default: 2,
        }
    })
    let userState = {
        loggedIn: false,
    }
    const userName = computed(() => userState.loggedIn ? "Jonas" : "Log in/Sign up")
</script>


<template>
    <div id="header">
        <div id="logo">
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
            <div v-if="mail > 0" id="notification-dot"></div>
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
        align-items: center;
    }

    #logo {
        grid-area: logo;
        font-size: 48px;
        font-weight: bold;
        padding-left: 40px;
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
        grid-template-columns: 2fr auto 4fr auto;
        align-items: center;
        margin-right: 40px;
        row-gap: 10px;
        
    }
    #home, #categories, #sell {
        font-size: 20px;
    }
    #inbox {
        grid-area: inbox;
        font-size: 20px;
        justify-self: right;
    }
    #inbox-icon {
        grid-area: inbox-icon;
        font-size: 25px;
        justify-self: left;
        padding-left: 3px;
    }
    #username {
        grid-area: user;
        font-size: 20px;
        justify-self: right;
    }
    #login-icon {
        grid-area: user-icon;
        font-size: 25px;
        padding-right: 10px;
        justify-self: right;
    }

    #searchbar {
        grid-area: searchbar;
        align-self: center;
    }

    .icons {
        font-size: 25px;
    }
    #logo:hover, #home:hover, #categories:hover, #sell:hover, #inbox:hover, #username:hover {
        cursor: pointer;
        border-bottom: 2px solid #000;
    }

    #notification-dot {
        grid-area: inbox-icon;
        justify-self: right;
        align-self: start;
        width: 10px;
        height: 10px;
        background-color: red;
        border-radius: 50%;
        border: 2px solid white;
        transform: translate(50%, -30%);
    }

    @media (max-width:900px) {
        #home, #categories, #sell {
            font-size: 15px; 
        }
        #inbox, #username {
            font-size: 15px; 
        }
    }

    @media (max-width: 768px) {
        #home, #categories, #sell, #inbox, #username {
            display: none; 
        }

        .icons {
            font-size: 20px; 
        }

        #logo {
            font-size: 20px; 
            padding-left: 20px;
        }
    }

</style>