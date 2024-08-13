import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAnexoServicoContabilEmpresa } from '../anexo-servico-contabil-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../anexo-servico-contabil-empresa.test-samples';

import { AnexoServicoContabilEmpresaService, RestAnexoServicoContabilEmpresa } from './anexo-servico-contabil-empresa.service';

const requireRestSample: RestAnexoServicoContabilEmpresa = {
  ...sampleWithRequiredData,
  dataHoraUpload: sampleWithRequiredData.dataHoraUpload?.toJSON(),
};

describe('AnexoServicoContabilEmpresa Service', () => {
  let service: AnexoServicoContabilEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IAnexoServicoContabilEmpresa | IAnexoServicoContabilEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AnexoServicoContabilEmpresaService);
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

    it('should create a AnexoServicoContabilEmpresa', () => {
      const anexoServicoContabilEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(anexoServicoContabilEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AnexoServicoContabilEmpresa', () => {
      const anexoServicoContabilEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(anexoServicoContabilEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AnexoServicoContabilEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AnexoServicoContabilEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AnexoServicoContabilEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAnexoServicoContabilEmpresaToCollectionIfMissing', () => {
      it('should add a AnexoServicoContabilEmpresa to an empty array', () => {
        const anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa = sampleWithRequiredData;
        expectedResult = service.addAnexoServicoContabilEmpresaToCollectionIfMissing([], anexoServicoContabilEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoServicoContabilEmpresa);
      });

      it('should not add a AnexoServicoContabilEmpresa to an array that contains it', () => {
        const anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa = sampleWithRequiredData;
        const anexoServicoContabilEmpresaCollection: IAnexoServicoContabilEmpresa[] = [
          {
            ...anexoServicoContabilEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAnexoServicoContabilEmpresaToCollectionIfMissing(
          anexoServicoContabilEmpresaCollection,
          anexoServicoContabilEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AnexoServicoContabilEmpresa to an array that doesn't contain it", () => {
        const anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa = sampleWithRequiredData;
        const anexoServicoContabilEmpresaCollection: IAnexoServicoContabilEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addAnexoServicoContabilEmpresaToCollectionIfMissing(
          anexoServicoContabilEmpresaCollection,
          anexoServicoContabilEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoServicoContabilEmpresa);
      });

      it('should add only unique AnexoServicoContabilEmpresa to an array', () => {
        const anexoServicoContabilEmpresaArray: IAnexoServicoContabilEmpresa[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const anexoServicoContabilEmpresaCollection: IAnexoServicoContabilEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoServicoContabilEmpresaToCollectionIfMissing(
          anexoServicoContabilEmpresaCollection,
          ...anexoServicoContabilEmpresaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa = sampleWithRequiredData;
        const anexoServicoContabilEmpresa2: IAnexoServicoContabilEmpresa = sampleWithPartialData;
        expectedResult = service.addAnexoServicoContabilEmpresaToCollectionIfMissing(
          [],
          anexoServicoContabilEmpresa,
          anexoServicoContabilEmpresa2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(anexoServicoContabilEmpresa);
        expect(expectedResult).toContain(anexoServicoContabilEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa = sampleWithRequiredData;
        expectedResult = service.addAnexoServicoContabilEmpresaToCollectionIfMissing([], null, anexoServicoContabilEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(anexoServicoContabilEmpresa);
      });

      it('should return initial array if no AnexoServicoContabilEmpresa is added', () => {
        const anexoServicoContabilEmpresaCollection: IAnexoServicoContabilEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addAnexoServicoContabilEmpresaToCollectionIfMissing(
          anexoServicoContabilEmpresaCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(anexoServicoContabilEmpresaCollection);
      });
    });

    describe('compareAnexoServicoContabilEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAnexoServicoContabilEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAnexoServicoContabilEmpresa(entity1, entity2);
        const compareResult2 = service.compareAnexoServicoContabilEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAnexoServicoContabilEmpresa(entity1, entity2);
        const compareResult2 = service.compareAnexoServicoContabilEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAnexoServicoContabilEmpresa(entity1, entity2);
        const compareResult2 = service.compareAnexoServicoContabilEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
