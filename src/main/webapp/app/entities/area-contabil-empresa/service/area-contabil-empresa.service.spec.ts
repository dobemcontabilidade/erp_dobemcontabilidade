import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAreaContabilEmpresa } from '../area-contabil-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../area-contabil-empresa.test-samples';

import { AreaContabilEmpresaService } from './area-contabil-empresa.service';

const requireRestSample: IAreaContabilEmpresa = {
  ...sampleWithRequiredData,
};

describe('AreaContabilEmpresa Service', () => {
  let service: AreaContabilEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAreaContabilEmpresa | IAreaContabilEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AreaContabilEmpresaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a AreaContabilEmpresa', () => {
      const areaContabilEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(areaContabilEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AreaContabilEmpresa', () => {
      const areaContabilEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(areaContabilEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AreaContabilEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AreaContabilEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AreaContabilEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAreaContabilEmpresaToCollectionIfMissing', () => {
      it('should add a AreaContabilEmpresa to an empty array', () => {
        const areaContabilEmpresa: IAreaContabilEmpresa = sampleWithRequiredData;
        expectedResult = service.addAreaContabilEmpresaToCollectionIfMissing([], areaContabilEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaContabilEmpresa);
      });

      it('should not add a AreaContabilEmpresa to an array that contains it', () => {
        const areaContabilEmpresa: IAreaContabilEmpresa = sampleWithRequiredData;
        const areaContabilEmpresaCollection: IAreaContabilEmpresa[] = [
          {
            ...areaContabilEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAreaContabilEmpresaToCollectionIfMissing(areaContabilEmpresaCollection, areaContabilEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AreaContabilEmpresa to an array that doesn't contain it", () => {
        const areaContabilEmpresa: IAreaContabilEmpresa = sampleWithRequiredData;
        const areaContabilEmpresaCollection: IAreaContabilEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addAreaContabilEmpresaToCollectionIfMissing(areaContabilEmpresaCollection, areaContabilEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaContabilEmpresa);
      });

      it('should add only unique AreaContabilEmpresa to an array', () => {
        const areaContabilEmpresaArray: IAreaContabilEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const areaContabilEmpresaCollection: IAreaContabilEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAreaContabilEmpresaToCollectionIfMissing(areaContabilEmpresaCollection, ...areaContabilEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const areaContabilEmpresa: IAreaContabilEmpresa = sampleWithRequiredData;
        const areaContabilEmpresa2: IAreaContabilEmpresa = sampleWithPartialData;
        expectedResult = service.addAreaContabilEmpresaToCollectionIfMissing([], areaContabilEmpresa, areaContabilEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaContabilEmpresa);
        expect(expectedResult).toContain(areaContabilEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const areaContabilEmpresa: IAreaContabilEmpresa = sampleWithRequiredData;
        expectedResult = service.addAreaContabilEmpresaToCollectionIfMissing([], null, areaContabilEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaContabilEmpresa);
      });

      it('should return initial array if no AreaContabilEmpresa is added', () => {
        const areaContabilEmpresaCollection: IAreaContabilEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAreaContabilEmpresaToCollectionIfMissing(areaContabilEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(areaContabilEmpresaCollection);
      });
    });

    describe('compareAreaContabilEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAreaContabilEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAreaContabilEmpresa(entity1, entity2);
        const compareResult2 = service.compareAreaContabilEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAreaContabilEmpresa(entity1, entity2);
        const compareResult2 = service.compareAreaContabilEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAreaContabilEmpresa(entity1, entity2);
        const compareResult2 = service.compareAreaContabilEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
