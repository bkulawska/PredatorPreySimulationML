package visualization;

import animal.AnimalDNA;

public record DNACounter(
        AnimalDNA dna,
        Integer counter
) { }
