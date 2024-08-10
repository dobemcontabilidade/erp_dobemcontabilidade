import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { OpcaoNomeFantasiaEmpresaDetailComponent } from './opcao-nome-fantasia-empresa-detail.component';

describe('OpcaoNomeFantasiaEmpresa Management Detail Component', () => {
  let comp: OpcaoNomeFantasiaEmpresaDetailComponent;
  let fixture: ComponentFixture<OpcaoNomeFantasiaEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OpcaoNomeFantasiaEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: OpcaoNomeFantasiaEmpresaDetailComponent,
              resolve: { opcaoNomeFantasiaEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(OpcaoNomeFantasiaEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OpcaoNomeFantasiaEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load opcaoNomeFantasiaEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', OpcaoNomeFantasiaEmpresaDetailComponent);

      // THEN
      expect(instance.opcaoNomeFantasiaEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
    });
  });

  describe('PreviousState', () => {
    it('Should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
