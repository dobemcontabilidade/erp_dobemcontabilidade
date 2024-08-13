import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAreaContabilAssinaturaEmpresa } from '../area-contabil-assinatura-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../area-contabil-assinatura-empresa.test-samples';

import { AreaContabilAssinaturaEmpresaService, RestAreaContabilAssinaturaEmpresa } from './area-contabil-assinatura-empresa.service';

const requireRestSample: RestAreaContabilAssinaturaEmpresa = {
  ...sampleWithRequiredData,
  dataAtribuicao: sampleWithRequiredData.dataAtribuicao?.toJSON(),
  dataRevogacao: sampleWithRequiredData.dataRevogacao?.toJSON(),
};

describe('AreaContabilAssinaturaEmpresa Service', () => {
  let service: AreaContabilAssinaturaEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAreaContabilAssinaturaEmpresa | IAreaContabilAssinaturaEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AreaContabilAssinaturaEmpresaService);
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

    it('should create a AreaContabilAssinaturaEmpresa', () => {
      const areaContabilAssinaturaEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(areaContabilAssinaturaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AreaContabilAssinaturaEmpresa', () => {
      const areaContabilAssinaturaEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(areaContabilAssinaturaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AreaContabilAssinaturaEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AreaContabilAssinaturaEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AreaContabilAssinaturaEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAreaContabilAssinaturaEmpresaToCollectionIfMissing', () => {
      it('should add a AreaContabilAssinaturaEmpresa to an empty array', () => {
        const areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa = sampleWithRequiredData;
        expectedResult = service.addAreaContabilAssinaturaEmpresaToCollectionIfMissing([], areaContabilAssinaturaEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaContabilAssinaturaEmpresa);
      });

      it('should not add a AreaContabilAssinaturaEmpresa to an array that contains it', () => {
        const areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa = sampleWithRequiredData;
        const areaContabilAssinaturaEmpresaCollection: IAreaContabilAssinaturaEmpresa[] = [
          {
            ...areaContabilAssinaturaEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAreaContabilAssinaturaEmpresaToCollectionIfMissing(
          areaContabilAssinaturaEmpresaCollection,
          areaContabilAssinaturaEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AreaContabilAssinaturaEmpresa to an array that doesn't contain it", () => {
        const areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa = sampleWithRequiredData;
        const areaContabilAssinaturaEmpresaCollection: IAreaContabilAssinaturaEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addAreaContabilAssinaturaEmpresaToCollectionIfMissing(
          areaContabilAssinaturaEmpresaCollection,
          areaContabilAssinaturaEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaContabilAssinaturaEmpresa);
      });

      it('should add only unique AreaContabilAssinaturaEmpresa to an array', () => {
        const areaContabilAssinaturaEmpresaArray: IAreaContabilAssinaturaEmpresa[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const areaContabilAssinaturaEmpresaCollection: IAreaContabilAssinaturaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAreaContabilAssinaturaEmpresaToCollectionIfMissing(
          areaContabilAssinaturaEmpresaCollection,
          ...areaContabilAssinaturaEmpresaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa = sampleWithRequiredData;
        const areaContabilAssinaturaEmpresa2: IAreaContabilAssinaturaEmpresa = sampleWithPartialData;
        expectedResult = service.addAreaContabilAssinaturaEmpresaToCollectionIfMissing(
          [],
          areaContabilAssinaturaEmpresa,
          areaContabilAssinaturaEmpresa2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaContabilAssinaturaEmpresa);
        expect(expectedResult).toContain(areaContabilAssinaturaEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa = sampleWithRequiredData;
        expectedResult = service.addAreaContabilAssinaturaEmpresaToCollectionIfMissing([], null, areaContabilAssinaturaEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaContabilAssinaturaEmpresa);
      });

      it('should return initial array if no AreaContabilAssinaturaEmpresa is added', () => {
        const areaContabilAssinaturaEmpresaCollection: IAreaContabilAssinaturaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAreaContabilAssinaturaEmpresaToCollectionIfMissing(
          areaContabilAssinaturaEmpresaCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(areaContabilAssinaturaEmpresaCollection);
      });
    });

    describe('compareAreaContabilAssinaturaEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAreaContabilAssinaturaEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAreaContabilAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareAreaContabilAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAreaContabilAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareAreaContabilAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAreaContabilAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareAreaContabilAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
