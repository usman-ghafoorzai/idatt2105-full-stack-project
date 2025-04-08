<script setup>
    import ItemPicture from '../components/ItemPicture.vue';
    import ItemDescription from '../components/ItemDescription.vue';
    import SellerInfo from '../components/SellerInfo.vue';
    import ReserveButton from '../components/ReserveButton.vue';
    import BuyNowButton from '../components/BuyNowButton.vue';
</script>

<template>
    <div id="item-view-container">
        <div id="left-section">
            <div id="image-seller-information">
               <ItemPicture
                :images="['src/assets/images/boat.jpg', 'src/assets/images/Thor.png']"
                id="item-picture"
                />
                <SellerInfo
                    :name="'John Doe'"
                    :phoneNumber="'12345678'"
                    :email="'test@gmail.com'"
                /> 
            </div>
            
        </div>
        
        <div id="right-section">
            <div id="item-description-buttons">
                <ItemDescription
                    title="Bayliner VR 5 Cuddy OB Lite brukt"
                    price="10000"
                    description="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
                    image="src/assets/images/boat.jpg"
                    location="Oslo"
                    :categories="['Boat', 'Used']"
                ></ItemDescription>
                
                <div id="buttons-container">
                    <BuyNowButton></BuyNowButton>
                    <ReserveButton></ReserveButton>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    #item-view-container {
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: flex-start;
        gap: 150px;
        width: 100%;
        max-width: 1500px;
        margin: 0 auto;
        padding: 20px;
        box-sizing: border-box;
    }
    #left-section {
        display: flex;
        flex: 1;
        flex-direction: column;
        gap: 20px;
    }
    #right-section {
        display: flex;
        flex: 1;
        flex-direction: column;
        gap: 20px;
    }
    #item-description-buttons {
        display: grid;
        grid-template-rows: auto auto;
        gap: 20px;
        width: auto;
        margin-right: 10%;
    }
    #image-seller-information {
        display: flex;
        flex-direction: column;
        gap: 40px;
    }
    #buttons-container {
        display: flex;
        flex-direction: row;
        gap: 20px;
        justify-content: flex-start;
    }

    @media (max-width: 768px) {
    #item-view-container {
        flex-direction: column; /* Stack components vertically */
        gap: 20px;
        align-items: center;;
    }


}
</style>