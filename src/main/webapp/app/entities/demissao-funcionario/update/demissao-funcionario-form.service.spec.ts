import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../demissao-funcionario.test-samples';

import { DemissaoFuncionarioFormService } from './demissao-funcionario-form.service';

describe('DemissaoFuncionario Form Service', () => {
  let service: DemissaoFuncionarioFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DemissaoFuncionarioFormService);
  });

  describe('Service methods', () => {
    describe('createDemissaoFuncionarioFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDemissaoFuncionarioFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeroCertidaoObito: expect.any(Object),
            cnpjEmpresaSucessora: expect.any(Object),
            saldoFGTS: expect.any(Object),
            valorPensao: expect.any(Object),
            valorPensaoFgts: expect.any(Object),
            percentualPensao: expect.any(Object),
            percentualFgts: expect.any(Object),
            diasAvisoPrevio: expect.any(Object),
            dataAvisoPrevio: expect.any(Object),
            dataPagamento: expect.any(Object),
            dataAfastamento: expect.any(Object),
            urlDemissional: expect.any(Object),
            calcularRecisao: expect.any(Object),
            pagar13Recisao: expect.any(Object),
            jornadaTrabalhoCumpridaSemana: expect.any(Object),
            sabadoCompesado: expect.any(Object),
            novoVinculoComprovado: expect.any(Object),
            dispensaAvisoPrevio: expect.any(Object),
            fgtsArrecadadoGuia: expect.any(Object),
            avisoPrevioTrabalhadoRecebido: expect.any(Object),
            recolherFgtsMesAnterior: expect.any(Object),
            avisoPrevioIndenizado: expect.any(Object),
            cumprimentoAvisoPrevio: expect.any(Object),
            avisoPrevio: expect.any(Object),
            situacaoDemissao: expect.any(Object),
            tipoDemissao: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });

      it('passing IDemissaoFuncionario should create a new form with FormGroup', () => {
        const formGroup = service.createDemissaoFuncionarioFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            numeroCertidaoObito: expect.any(Object),
            cnpjEmpresaSucessora: expect.any(Object),
            saldoFGTS: expect.any(Object),
            valorPensao: expect.any(Object),
            valorPensaoFgts: expect.any(Object),
            percentualPensao: expect.any(Object),
            percentualFgts: expect.any(Object),
            diasAvisoPrevio: expect.any(Object),
            dataAvisoPrevio: expect.any(Object),
            dataPagamento: expect.any(Object),
            dataAfastamento: expect.any(Object),
            urlDemissional: expect.any(Object),
            calcularRecisao: expect.any(Object),
            pagar13Recisao: expect.any(Object),
            jornadaTrabalhoCumpridaSemana: expect.any(Object),
            sabadoCompesado: expect.any(Object),
            novoVinculoComprovado: expect.any(Object),
            dispensaAvisoPrevio: expect.any(Object),
            fgtsArrecadadoGuia: expect.any(Object),
            avisoPrevioTrabalhadoRecebido: expect.any(Object),
            recolherFgtsMesAnterior: expect.any(Object),
            avisoPrevioIndenizado: expect.any(Object),
            cumprimentoAvisoPrevio: expect.any(Object),
            avisoPrevio: expect.any(Object),
            situacaoDemissao: expect.any(Object),
            tipoDemissao: expect.any(Object),
            funcionario: expect.any(Object),
          }),
        );
      });
    });

    describe('getDemissaoFuncionario', () => {
      it('should return NewDemissaoFuncionario for default DemissaoFuncionario initial value', () => {
        const formGroup = service.createDemissaoFuncionarioFormGroup(sampleWithNewData);

        const demissaoFuncionario = service.getDemissaoFuncionario(formGroup) as any;

        expect(demissaoFuncionario).toMatchObject(sampleWithNewData);
      });

      it('should return NewDemissaoFuncionario for empty DemissaoFuncionario initial value', () => {
        const formGroup = service.createDemissaoFuncionarioFormGroup();

        const demissaoFuncionario = service.getDemissaoFuncionario(formGroup) as any;

        expect(demissaoFuncionario).toMatchObject({});
      });

      it('should return IDemissaoFuncionario', () => {
        const formGroup = service.createDemissaoFuncionarioFormGroup(sampleWithRequiredData);

        const demissaoFuncionario = service.getDemissaoFuncionario(formGroup) as any;

        expect(demissaoFuncionario).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDemissaoFuncionario should not enable id FormControl', () => {
        const formGroup = service.createDemissaoFuncionarioFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDemissaoFuncionario should disable id FormControl', () => {
        const formGroup = service.createDemissaoFuncionarioFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
