import ItemPicture from '../components/ItemPicture.vue';
import { describe, it, expect } from 'vitest';
import { mount } from '@vue/test-utils';


describe('ItemPicture.vue', () => {
    const images = [
        'src/assets/images/boat.jpg',
        'src/assets/images/Thor.png',
    ]

    it('renders the image with the correct src and alt attributes', () => {
        const wrapper = mount(ItemPicture, {
            props: { images },
        });
        const img = wrapper.find('img');
        expect(img.attributes('src')).toBe(images[0]);
    });

    it('navigates to the next image when clicked', async() => {
        const wrapper = mount(ItemPicture, {
            props: { images },
        });

        const nextButton = wrapper.find('.next');
        await nextButton.trigger('click');

        const img = wrapper.find('img');
        expect(img.attributes('src')).toBe(images[1]);
    });

    it('navigates to the previous image when clicked', async() => {
        const wrapper = mount(ItemPicture, {
            props: { images },
        });

        const nextButton = wrapper.find('.next');
        await nextButton.trigger('click');

        const prevButton = wrapper.find('.prev');
        await prevButton.trigger('click');

        const img = wrapper.find('img');
        expect(img.attributes('src')).toBe(images[0]);
    });

    it('loops to the last image when on the first image and "prev" is clicked', async () => {
        const wrapper = mount(ItemPicture, {
            props: { images },
        });

        const prevButton = wrapper.find('.prev');
        await prevButton.trigger('click');

        const img = wrapper.find('img');
        expect(img.attributes('src')).toBe(images[images.length - 1]);
    });
    });