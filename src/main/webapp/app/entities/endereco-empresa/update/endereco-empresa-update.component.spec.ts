import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { ICidade } from 'app/entities/cidade/cidade.model';
import { CidadeService } from 'app/entities/cidade/service/cidade.service';
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { PessoajuridicaService } from 'app/entities/pessoajuridica/service/pessoajuridica.service';
import { IEnderecoEmpresa } from '../endereco-empresa.model';
import { EnderecoEmpresaService } from '../service/endereco-empresa.service';
import { EnderecoEmpresaFormService } from './endereco-empresa-form.service';

import { EnderecoEmpresaUpdateComponent } from './endereco-empresa-update.component';

describe('EnderecoEmpresa Management Update Component', () => {
  let comp: EnderecoEmpresaUpdateComponent;
  let fixture: ComponentFixture<EnderecoEmpresaUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let enderecoEmpresaFormService: EnderecoEmpresaFormService;
  let enderecoEmpresaService: EnderecoEmpresaService;
  let cidadeService: CidadeService;
  let pessoajuridicaService: PessoajuridicaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EnderecoEmpresaUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EnderecoEmpresaUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EnderecoEmpresaUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    enderecoEmpresaFormService = TestBed.inject(EnderecoEmpresaFormService);
    enderecoEmpresaService = TestBed.inject(EnderecoEmpresaService);
    cidadeService = TestBed.inject(CidadeService);
    pessoajuridicaService = TestBed.inject(PessoajuridicaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Cidade query and add missing value', () => {
      const enderecoEmpresa: IEnderecoEmpresa = { id: 456 };
      const cidade: ICidade = { id: 19250 };
      enderecoEmpresa.cidade = cidade;

      const cidadeCollection: ICidade[] = [{ id: 25690 }];
      jest.spyOn(cidadeService, 'query').mockReturnValue(of(new HttpResponse({ body: cidadeCollection })));
      const additionalCidades = [cidade];
      const expectedCollection: ICidade[] = [...additionalCidades, ...cidadeCollection];
      jest.spyOn(cidadeService, 'addCidadeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enderecoEmpresa });
      comp.ngOnInit();

      expect(cidadeService.query).toHaveBeenCalled();
      expect(cidadeService.addCidadeToCollectionIfMissing).toHaveBeenCalledWith(
        cidadeCollection,
        ...additionalCidades.map(expect.objectContaining),
      );
      expect(comp.cidadesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Pessoajuridica query and add missing value', () => {
      const enderecoEmpresa: IEnderecoEmpresa = { id: 456 };
      const pessoaJuridica: IPessoajuridica = { id: 8925 };
      enderecoEmpresa.pessoaJuridica = pessoaJuridica;

      const pessoajuridicaCollection: IPessoajuridica[] = [{ id: 26794 }];
      jest.spyOn(pessoajuridicaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoajuridicaCollection })));
      const additionalPessoajuridicas = [pessoaJuridica];
      const expectedCollection: IPessoajuridica[] = [...additionalPessoajuridicas, ...pessoajuridicaCollection];
      jest.spyOn(pessoajuridicaService, 'addPessoajuridicaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ enderecoEmpresa });
      comp.ngOnInit();

      expect(pessoajuridicaService.query).toHaveBeenCalled();
      expect(pessoajuridicaService.addPessoajuridicaToCollectionIfMissing).toHaveBeenCalledWith(
        pessoajuridicaCollection,
        ...additionalPessoajuridicas.map(expect.objectContaining),
      );
      expect(comp.pessoajuridicasSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const enderecoEmpresa: IEnderecoEmpresa = { id: 456 };
      const cidade: ICidade = { id: 4010 };
      enderecoEmpresa.cidade = cidade;
      const pessoaJuridica: IPessoajuridica = { id: 25183 };
      enderecoEmpresa.pessoaJuridica = pessoaJuridica;

      activatedRoute.data = of({ enderecoEmpresa });
      comp.ngOnInit();

      expect(comp.cidadesSharedCollection).toContain(cidade);
      expect(comp.pessoajuridicasSharedCollection).toContain(pessoaJuridica);
      expect(comp.enderecoEmpresa).toEqual(enderecoEmpresa);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoEmpresa>>();
      const enderecoEmpresa = { id: 123 };
      jest.spyOn(enderecoEmpresaFormService, 'getEnderecoEmpresa').mockReturnValue(enderecoEmpresa);
      jest.spyOn(enderecoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enderecoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(enderecoEmpresaFormService.getEnderecoEmpresa).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(enderecoEmpresaService.update).toHaveBeenCalledWith(expect.objectContaining(enderecoEmpresa));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoEmpresa>>();
      const enderecoEmpresa = { id: 123 };
      jest.spyOn(enderecoEmpresaFormService, 'getEnderecoEmpresa').mockReturnValue({ id: null });
      jest.spyOn(enderecoEmpresaService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoEmpresa: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: enderecoEmpresa }));
      saveSubject.complete();

      // THEN
      expect(enderecoEmpresaFormService.getEnderecoEmpresa).toHaveBeenCalled();
      expect(enderecoEmpresaService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEnderecoEmpresa>>();
      const enderecoEmpresa = { id: 123 };
      jest.spyOn(enderecoEmpresaService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ enderecoEmpresa });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(enderecoEmpresaService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareCidade', () => {
      it('Should forward to cidadeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(cidadeService, 'compareCidade');
        comp.compareCidade(entity, entity2);
        expect(cidadeService.compareCidade).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('comparePessoajuridica', () => {
      it('Should forward to pessoajuridicaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoajuridicaService, 'comparePessoajuridica');
        comp.comparePessoajuridica(entity, entity2);
        expect(pessoajuridicaService.comparePessoajuridica).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
