import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { EnderecoEmpresaDetailComponent } from './endereco-empresa-detail.component';

describe('EnderecoEmpresa Management Detail Component', () => {
  let comp: EnderecoEmpresaDetailComponent;
  let fixture: ComponentFixture<EnderecoEmpresaDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EnderecoEmpresaDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EnderecoEmpresaDetailComponent,
              resolve: { enderecoEmpresa: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EnderecoEmpresaDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EnderecoEmpresaDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load enderecoEmpresa on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EnderecoEmpresaDetailComponent);

      // THEN
      expect(instance.enderecoEmpresa()).toEqual(expect.objectContaining({ id: 123 }));
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
