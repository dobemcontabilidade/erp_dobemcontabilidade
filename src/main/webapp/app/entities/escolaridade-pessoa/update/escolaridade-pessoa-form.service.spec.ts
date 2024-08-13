import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../escolaridade-pessoa.test-samples';

import { EscolaridadePessoaFormService } from './escolaridade-pessoa-form.service';

describe('EscolaridadePessoa Form Service', () => {
  let service: EscolaridadePessoaFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EscolaridadePessoaFormService);
  });

  describe('Service methods', () => {
    describe('createEscolaridadePessoaFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEscolaridadePessoaFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomeInstituicao: expect.any(Object),
            anoConclusao: expect.any(Object),
            urlComprovanteEscolaridade: expect.any(Object),
            pessoa: expect.any(Object),
            escolaridade: expect.any(Object),
          }),
        );
      });

      it('passing IEscolaridadePessoa should create a new form with FormGroup', () => {
        const formGroup = service.createEscolaridadePessoaFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nomeInstituicao: expect.any(Object),
            anoConclusao: expect.any(Object),
            urlComprovanteEscolaridade: expect.any(Object),
            pessoa: expect.any(Object),
            escolaridade: expect.any(Object),
          }),
        );
      });
    });

    describe('getEscolaridadePessoa', () => {
      it('should return NewEscolaridadePessoa for default EscolaridadePessoa initial value', () => {
        const formGroup = service.createEscolaridadePessoaFormGroup(sampleWithNewData);

        const escolaridadePessoa = service.getEscolaridadePessoa(formGroup) as any;

        expect(escolaridadePessoa).toMatchObject(sampleWithNewData);
      });

      it('should return NewEscolaridadePessoa for empty EscolaridadePessoa initial value', () => {
        const formGroup = service.createEscolaridadePessoaFormGroup();

        const escolaridadePessoa = service.getEscolaridadePessoa(formGroup) as any;

        expect(escolaridadePessoa).toMatchObject({});
      });

      it('should return IEscolaridadePessoa', () => {
        const formGroup = service.createEscolaridadePessoaFormGroup(sampleWithRequiredData);

        const escolaridadePessoa = service.getEscolaridadePessoa(formGroup) as any;

        expect(escolaridadePessoa).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEscolaridadePessoa should not enable id FormControl', () => {
        const formGroup = service.createEscolaridadePessoaFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEscolaridadePessoa should disable id FormControl', () => {
        const formGroup = service.createEscolaridadePessoaFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
