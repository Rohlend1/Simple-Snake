package main;

public class FileProperties {
    private final String name;
    private final long size;
    private final long compressedSize;
    private final int compressionMethod;


    public FileProperties(String name, long size, long compressedSize, int compressionMethod){
        this.compressedSize = compressedSize;
        this.name = name;
        this.size = size;
        this.compressionMethod = compressionMethod;
    }

    public long getCompressionRatio(){
        return 100-((compressedSize*100)/size);
    }

    @Override
    public String toString() {
        if(size > 0){
            return String.format("%s %d Kb (%d Kb)\nсжатие: %d%%\nметод сжатия: %d", name,size/1024,compressedSize/1024,getCompressionRatio(),compressionMethod);
        }
        else return name;
    }
}
