import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IServicoContabilAssinaturaEmpresa } from '../servico-contabil-assinatura-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../servico-contabil-assinatura-empresa.test-samples';

import {
  ServicoContabilAssinaturaEmpresaService,
  RestServicoContabilAssinaturaEmpresa,
} from './servico-contabil-assinatura-empresa.service';

const requireRestSample: RestServicoContabilAssinaturaEmpresa = {
  ...sampleWithRequiredData,
  dataLegal: sampleWithRequiredData.dataLegal?.toJSON(),
  dataAdmin: sampleWithRequiredData.dataAdmin?.toJSON(),
};

describe('ServicoContabilAssinaturaEmpresa Service', () => {
  let service: ServicoContabilAssinaturaEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IServicoContabilAssinaturaEmpresa | IServicoContabilAssinaturaEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ServicoContabilAssinaturaEmpresaService);
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

    it('should create a ServicoContabilAssinaturaEmpresa', () => {
      const servicoContabilAssinaturaEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(servicoContabilAssinaturaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ServicoContabilAssinaturaEmpresa', () => {
      const servicoContabilAssinaturaEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(servicoContabilAssinaturaEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ServicoContabilAssinaturaEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ServicoContabilAssinaturaEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ServicoContabilAssinaturaEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addServicoContabilAssinaturaEmpresaToCollectionIfMissing', () => {
      it('should add a ServicoContabilAssinaturaEmpresa to an empty array', () => {
        const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = sampleWithRequiredData;
        expectedResult = service.addServicoContabilAssinaturaEmpresaToCollectionIfMissing([], servicoContabilAssinaturaEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabilAssinaturaEmpresa);
      });

      it('should not add a ServicoContabilAssinaturaEmpresa to an array that contains it', () => {
        const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = sampleWithRequiredData;
        const servicoContabilAssinaturaEmpresaCollection: IServicoContabilAssinaturaEmpresa[] = [
          {
            ...servicoContabilAssinaturaEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addServicoContabilAssinaturaEmpresaToCollectionIfMissing(
          servicoContabilAssinaturaEmpresaCollection,
          servicoContabilAssinaturaEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ServicoContabilAssinaturaEmpresa to an array that doesn't contain it", () => {
        const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = sampleWithRequiredData;
        const servicoContabilAssinaturaEmpresaCollection: IServicoContabilAssinaturaEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addServicoContabilAssinaturaEmpresaToCollectionIfMissing(
          servicoContabilAssinaturaEmpresaCollection,
          servicoContabilAssinaturaEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabilAssinaturaEmpresa);
      });

      it('should add only unique ServicoContabilAssinaturaEmpresa to an array', () => {
        const servicoContabilAssinaturaEmpresaArray: IServicoContabilAssinaturaEmpresa[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const servicoContabilAssinaturaEmpresaCollection: IServicoContabilAssinaturaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilAssinaturaEmpresaToCollectionIfMissing(
          servicoContabilAssinaturaEmpresaCollection,
          ...servicoContabilAssinaturaEmpresaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = sampleWithRequiredData;
        const servicoContabilAssinaturaEmpresa2: IServicoContabilAssinaturaEmpresa = sampleWithPartialData;
        expectedResult = service.addServicoContabilAssinaturaEmpresaToCollectionIfMissing(
          [],
          servicoContabilAssinaturaEmpresa,
          servicoContabilAssinaturaEmpresa2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(servicoContabilAssinaturaEmpresa);
        expect(expectedResult).toContain(servicoContabilAssinaturaEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa = sampleWithRequiredData;
        expectedResult = service.addServicoContabilAssinaturaEmpresaToCollectionIfMissing(
          [],
          null,
          servicoContabilAssinaturaEmpresa,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(servicoContabilAssinaturaEmpresa);
      });

      it('should return initial array if no ServicoContabilAssinaturaEmpresa is added', () => {
        const servicoContabilAssinaturaEmpresaCollection: IServicoContabilAssinaturaEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addServicoContabilAssinaturaEmpresaToCollectionIfMissing(
          servicoContabilAssinaturaEmpresaCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(servicoContabilAssinaturaEmpresaCollection);
      });
    });

    describe('compareServicoContabilAssinaturaEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareServicoContabilAssinaturaEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareServicoContabilAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareServicoContabilAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareServicoContabilAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareServicoContabilAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareServicoContabilAssinaturaEmpresa(entity1, entity2);
        const compareResult2 = service.compareServicoContabilAssinaturaEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
