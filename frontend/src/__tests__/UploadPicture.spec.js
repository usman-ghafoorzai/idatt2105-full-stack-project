import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import { mount } from '@vue/test-utils';
import UploadPicture from '../components/UploadPicture.vue';
import genericAvatar from '../assets/generic-avatar.png';

describe('UploadPicture.vue', () => {
  let wrapper;

  beforeEach(() => {
    wrapper = mount(UploadPicture);
    vi.stubGlobal('URL', {createObjectURL: vi.fn(() => 'blob:mock-url')});
  });

  afterEach(() => {
    vi.unstubAllGlobals();
  });

  it('renders the default generic avatar', () => {
    const img = wrapper.find('#profile-pic');
    expect(img.exists()).toBe(true);
    expect(img.attributes('src')).toBe(genericAvatar);
  });

  it('triggers file input when image is clicked', async () => {
    const mockClick = vi.fn();
    const fileInput = wrapper.find('input[type="file"]').element;
    fileInput.click = mockClick;

    wrapper.vm.$refs = {fileInput};

    const imageContainer = wrapper.find('.image-container');
    await imageContainer.trigger('click');

    expect(mockClick).toHaveBeenCalled();
  });

  it('updates the profile picture when a file is selected', async () => {
    const mockFile = new File(['dummy'], 'avatar.png', {type: 'image/png'});

    const input = wrapper.find('input[type="file"]');

    const changeEvent = new Event('change');
    Object.defineProperty(changeEvent, 'target', {
      writable: false,
      value: {files: [mockFile]}
    });

    await input.element.dispatchEvent(changeEvent);
    await wrapper.vm.$nextTick();

    const updatedImg = wrapper.find('#profile-pic');
    expect(updatedImg.attributes('src')).toBe('blob:mock-url');
  });
});
