package com.aatrox.web.controller;

import com.aatrox.common.utils.EmojiCharacterUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author aatrox
 * @desc
 * @date 2019-08-09
 */
@RestController
@RequestMapping("/emoji")
public class EmojiController {
    @PostMapping("/save")
    public String save(@RequestBody String message) {
        String escape = EmojiCharacterUtil.escape(message);
        System.out.println(escape);
        System.out.println(EmojiCharacterUtil.reverse(escape));
        System.out.println(EmojiCharacterUtil.filter(message));
        return null;
    }
}
