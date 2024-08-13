import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../escolaridade.test-samples';

import { EscolaridadeFormService } from './escolaridade-form.service';

describe('Escolaridade Form Service', () => {
  let service: EscolaridadeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EscolaridadeFormService);
  });

  describe('Service methods', () => {
    describe('createEscolaridadeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEscolaridadeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });

      it('passing IEscolaridade should create a new form with FormGroup', () => {
        const formGroup = service.createEscolaridadeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nome: expect.any(Object),
            descricao: expect.any(Object),
          }),
        );
      });
    });

    describe('getEscolaridade', () => {
      it('should return NewEscolaridade for default Escolaridade initial value', () => {
        const formGroup = service.createEscolaridadeFormGroup(sampleWithNewData);

        const escolaridade = service.getEscolaridade(formGroup) as any;

        expect(escolaridade).toMatchObject(sampleWithNewData);
      });

      it('should return NewEscolaridade for empty Escolaridade initial value', () => {
        const formGroup = service.createEscolaridadeFormGroup();

        const escolaridade = service.getEscolaridade(formGroup) as any;

        expect(escolaridade).toMatchObject({});
      });

      it('should return IEscolaridade', () => {
        const formGroup = service.createEscolaridadeFormGroup(sampleWithRequiredData);

        const escolaridade = service.getEscolaridade(formGroup) as any;

        expect(escolaridade).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEscolaridade should not enable id FormControl', () => {
        const formGroup = service.createEscolaridadeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEscolaridade should disable id FormControl', () => {
        const formGroup = service.createEscolaridadeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
