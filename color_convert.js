let fs = require('fs');
let path = require('path')
let c = require("culori")
let color_file = fs.readFileSync(path.resolve(process.argv[2].replace("~", process.env.HOME)), 'utf-8')

let parsed = color_file
    .split('\n')
    .filter(v => v.length > 1)
    .map(l => {
        // it really doesn't like this inital space on the hex values
        let d = l.replace(': ', ':').split(':');
        return `    public static Color ${d[0].replace('--color-', '').replace('-', '')} = new Color(0x${c.formatHex(c.parse(d[1].replace(';', ''))).replace("#", '')});`
    })
    .join("\n")

let outfile = `
package Utils;
import java.awt.Color;

public abstract class ParsedColors {
    private ParsedColors() {}

${parsed}

}
`

fs.writeFileSync(path.resolve("./ParsedColors.java"), outfile);
console.log("Done!")
