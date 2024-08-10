import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/service/pessoa.service';
import { AdministradorService } from '../service/administrador.service';
import { IAdministrador } from '../administrador.model';
import { AdministradorFormService } from './administrador-form.service';

import { AdministradorUpdateComponent } from './administrador-update.component';

describe('Administrador Management Update Component', () => {
  let comp: AdministradorUpdateComponent;
  let fixture: ComponentFixture<AdministradorUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let administradorFormService: AdministradorFormService;
  let administradorService: AdministradorService;
  let pessoaService: PessoaService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AdministradorUpdateComponent],
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
      .overrideTemplate(AdministradorUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AdministradorUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    administradorFormService = TestBed.inject(AdministradorFormService);
    administradorService = TestBed.inject(AdministradorService);
    pessoaService = TestBed.inject(PessoaService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call pessoa query and add missing value', () => {
      const administrador: IAdministrador = { id: 456 };
      const pessoa: IPessoa = { id: 23559 };
      administrador.pessoa = pessoa;

      const pessoaCollection: IPessoa[] = [{ id: 12967 }];
      jest.spyOn(pessoaService, 'query').mockReturnValue(of(new HttpResponse({ body: pessoaCollection })));
      const expectedCollection: IPessoa[] = [pessoa, ...pessoaCollection];
      jest.spyOn(pessoaService, 'addPessoaToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ administrador });
      comp.ngOnInit();

      expect(pessoaService.query).toHaveBeenCalled();
      expect(pessoaService.addPessoaToCollectionIfMissing).toHaveBeenCalledWith(pessoaCollection, pessoa);
      expect(comp.pessoasCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const administrador: IAdministrador = { id: 456 };
      const pessoa: IPessoa = { id: 24996 };
      administrador.pessoa = pessoa;

      activatedRoute.data = of({ administrador });
      comp.ngOnInit();

      expect(comp.pessoasCollection).toContain(pessoa);
      expect(comp.administrador).toEqual(administrador);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdministrador>>();
      const administrador = { id: 123 };
      jest.spyOn(administradorFormService, 'getAdministrador').mockReturnValue(administrador);
      jest.spyOn(administradorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: administrador }));
      saveSubject.complete();

      // THEN
      expect(administradorFormService.getAdministrador).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(administradorService.update).toHaveBeenCalledWith(expect.objectContaining(administrador));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdministrador>>();
      const administrador = { id: 123 };
      jest.spyOn(administradorFormService, 'getAdministrador').mockReturnValue({ id: null });
      jest.spyOn(administradorService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrador: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: administrador }));
      saveSubject.complete();

      // THEN
      expect(administradorFormService.getAdministrador).toHaveBeenCalled();
      expect(administradorService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAdministrador>>();
      const administrador = { id: 123 };
      jest.spyOn(administradorService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ administrador });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(administradorService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('comparePessoa', () => {
      it('Should forward to pessoaService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(pessoaService, 'comparePessoa');
        comp.comparePessoa(entity, entity2);
        expect(pessoaService.comparePessoa).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
