import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import ItemFrameComponent from '../components/ItemFrameComponent.vue';

describe('ItemFrameComponent.vue', () => {
    it('renders the component correctly', () => {
        const wrapper = mount(ItemFrameComponent);

        // Check if the component exists
        expect(wrapper.exists()).toBe(true);
    });

    it('renders the image with the correct src and alt attributes', () => {
        const wrapper = mount(ItemFrameComponent);

        const img = wrapper.find('img');
        expect(img.exists()).toBe(true);
        expect(img.attributes('src')).toBe('/src/assets/images/boat.jpg');
        expect(img.attributes('alt')).toBe('Item Image');
    });

    it('renders the price tag with the correct text', () => {
        const wrapper = mount(ItemFrameComponent);

        const priceTag = wrapper.find('.price-tag');
        expect(priceTag.exists()).toBe(true);
        expect(priceTag.text()).toBe('10000 kr');
    });

    it('renders the item details with the correct text', () => {
        const wrapper = mount(ItemFrameComponent);

        const itemDetails = wrapper.find('.item-details');
        expect(itemDetails.exists()).toBe(true);
        expect(itemDetails.text()).toBe('Bayliner VR 5 Cuddy OB Lite brukt');
    });
});