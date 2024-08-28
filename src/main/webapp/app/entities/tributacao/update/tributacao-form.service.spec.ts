import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../tributacao.test-samples';

import { TributacaoFormService } from './tributacao-form.service';

describe('Tributacao Form Service', () => {
  let service: TributacaoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TributacaoFormService);
  });

  describe('Service methods', () => {
    describe('createTributacaoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createTributacaoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            situacao: expect.any(Object),
          }),
        );
      });

      it('passing ITributacao should create a new form with FormGroup', () => {
        const formGroup = service.createTributacaoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
            situacao: expect.any(Object),
          }),
        );
      });
    });

    describe('getTributacao', () => {
      it('should return NewTributacao for default Tributacao initial value', () => {
        const formGroup = service.createTributacaoFormGroup(sampleWithNewData);

        const tributacao = service.getTributacao(formGroup) as any;

        expect(tributacao).toMatchObject(sampleWithNewData);
      });

      it('should return NewTributacao for empty Tributacao initial value', () => {
        const formGroup = service.createTributacaoFormGroup();

        const tributacao = service.getTributacao(formGroup) as any;

        expect(tributacao).toMatchObject({});
      });

      it('should return ITributacao', () => {
        const formGroup = service.createTributacaoFormGroup(sampleWithRequiredData);

        const tributacao = service.getTributacao(formGroup) as any;

        expect(tributacao).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ITributacao should not enable id FormControl', () => {
        const formGroup = service.createTributacaoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewTributacao should disable id FormControl', () => {
        const formGroup = service.createTributacaoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
